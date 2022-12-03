pluginManagement {

  val springBootVersion: String by settings
  val springDependencyManagementVersion: String by settings
  val kotlinVersion: String by settings
  val kotlinSpringVersion: String by settings
  val owaspDependencyCheckVersion: String by settings
  val jacocoVersion: String by settings
  val gradleVersionsPluginVersion: String by settings
  val klintVersion: String by settings
  val detektVersion: String by settings
  val pmdVersion: String by settings

  repositories {
    maven(url = uri("https://plugins.gradle.org/m2/"))
    gradlePluginPortal()
    mavenCentral()
  }

  plugins {
    kotlin("jvm") version kotlinVersion // apply false
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    kotlin("plugin.spring") version kotlinSpringVersion
    id("org.owasp.dependencycheck") version owaspDependencyCheckVersion
    id("jacoco") version jacocoVersion
    id("com.github.ben-manes.versions") version gradleVersionsPluginVersion
    id("org.jlleitschuh.gradle.ktlint") version klintVersion
    id("io.gitlab.arturbosch.detekt") version detektVersion
    id("pmd") version pmdVersion
  }
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {

      version("snakeyaml", "1.33")
      version("spring-web", "5.3.24")

      library("snakeyaml-trouble", "org.yaml", "snakeyaml").versionRef("snakeyaml")
      library("spring-web-trouble", "org.springframework", "spring-web").versionRef("spring-web")
    }
  }
}

rootProject.name = "stringconcat"

include(
  "code-coverage-report",
  "sc-hello-world"
)
