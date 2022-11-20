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
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("io.projectreactor:reactor-test")
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
