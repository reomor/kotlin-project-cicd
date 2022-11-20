import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
  id("org.springframework.boot") apply false
  id("io.spring.dependency-management")
  id("java")
  kotlin("jvm")
  kotlin("plugin.spring")
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

  repositories {
    mavenCentral()
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

  the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
      mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}