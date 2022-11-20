pluginManagement {

  val springBootVersion: String by settings
  val springDependencyManagementVersion: String by settings
  val kotlinVersion: String by settings
  val kotlinSpringVersion: String by settings

  repositories {
    maven(url = uri("https://plugins.gradle.org/m2/"))
    gradlePluginPortal()
    mavenCentral()
  }

  plugins {
    kotlin("jvm") version kotlinVersion //apply false
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    kotlin("plugin.spring") version kotlinSpringVersion
  }
}

rootProject.name = "stringconcat"

include(
  "sc-hello-world"
)
