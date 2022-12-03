import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
  id("org.springframework.boot") apply false
  id("io.spring.dependency-management")
  id("java")
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.owasp.dependencycheck")
  jacoco
  id("com.github.ben-manes.versions")
  checkstyle
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

jacoco {
  val jacocoVersion: String by project
  toolVersion = jacocoVersion
}

// JAVA only
checkstyle {
  val checkstyleVersion: String by project
  toolVersion = checkstyleVersion
  configFile = file("${project.rootDir}/ci/checkstyle/google_checks.xml")
}

group = "com.github.reomor"
version = "0.0.1-SNAPSHOT"

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

allprojects {
  apply<JavaPlugin>()
  apply(plugin = "java")
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "org.owasp.dependencycheck")
  apply(plugin = "jacoco")
  apply(plugin = "com.github.ben-manes.versions")
  apply(plugin = "checkstyle")

  repositories {
    mavenCentral()
  }

  dependencyCheck {
    suppressionFile = "ci/owasp-exclusions.xml"
  }

  tasks.withType<DependencyUpdatesTask> {

    revision = "release"

    fun isNonStable(version: String): Boolean {
      val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
      val regex = "^[0-9,.v-]+(-r)?$".toRegex()
      val isStable = stableKeyword || regex.matches(version)
      return isStable.not()
    }

    rejectVersionIf {
      isNonStable(candidate.version)
    }

    resolutionStrategy {
      componentSelection {
        all {
          if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
            reject("Release candidate")
          }
        }
      }
    }
  }

  tasks.withType<Checkstyle> {
    setIncludes(setOf("**/*.java"))
    setExcludes(setOf("**/generated/**"))
  }

  configurations.all {
    resolutionStrategy {
      eachDependency {

        val snakeYmlDependencyModule = libs.snakeyaml.trouble.get().module
        val springWebDependencyModule = libs.spring.web.trouble.get().module

        val versionSelector = this.requested
        if (versionSelector.group == snakeYmlDependencyModule.group && versionSelector.name == snakeYmlDependencyModule.name) {
          this.useVersion(libs.versions.snakeyaml.get())
        }

        if (versionSelector.group == springWebDependencyModule.group && versionSelector.name == springWebDependencyModule.name) {
          this.useVersion(libs.versions.spring.web.get())
        }
      }
    }
  }

  gradle.projectsEvaluated {
    tasks.withType<JavaCompile> {
      if (project.hasProperty("warn")) {
        options.compilerArgs = options.compilerArgs + "-Xlint:unchecked" + "-Xlint:deprecation"
      } else {
        options.compilerArgs = options.compilerArgs + "-Xlint:-unchecked" + "-Xlint:-deprecation"
      }
    }
    tasks.withType<KotlinCompile> {
      kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
      }
    }
  }
}

subprojects {

  group = rootProject.group
  version = rootProject.version

  java {
    sourceCompatibility = rootProject.java.sourceCompatibility
    targetCompatibility = rootProject.java.targetCompatibility
  }

  dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  the<DependencyManagementExtension>().apply {

    val cucumberVersion: String by project

    imports {
      mavenBom(SpringBootPlugin.BOM_COORDINATES)
      mavenBom("io.cucumber:cucumber-bom:$cucumberVersion")
    }
  }

  tasks {
    check {
      dependsOn(jacocoTestCoverageVerification)
      dependsOn(dependencyCheckAnalyze)
      dependsOn(dependencyUpdates)
    }
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
