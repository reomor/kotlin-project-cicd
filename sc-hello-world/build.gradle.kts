plugins {
  id("org.springframework.boot")
  kotlin("jvm")
}

dependencies {

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testImplementation("org.mockito:mockito-core")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.junit.platform:junit-platform-suite")

  testImplementation("io.cucumber:cucumber-java")
  testImplementation("io.cucumber:cucumber-java8")
  testImplementation("io.cucumber:cucumber-spring")
  testImplementation("io.cucumber:cucumber-junit-platform-engine")
}

tasks {

  bootJar {
    enabled = false
  }

  jar {
    enabled = true
  }
}

tasks.test {
  useJUnitPlatform()
}
