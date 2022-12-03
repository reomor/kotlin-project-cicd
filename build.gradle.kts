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
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
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

  repositories {
    mavenCentral()
  }

  dependencyCheck {
    suppressionFile = "ci/owasp-exclusions.xml"
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
      dependsOn(dependencyCheckAnalyze)
    }
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
