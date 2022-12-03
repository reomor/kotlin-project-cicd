plugins {
  base
  id("jacoco-report-aggregation")
}

repositories {
  mavenCentral()
}

jacoco {
  val jacocoVersion: String by project
  toolVersion = jacocoVersion
}

dependencies {
  jacocoAggregation(project(":sc-hello-world"))
}

reporting {
  reports {
    val allTestCodeCoverageReport by creating(JacocoCoverageReport::class) {
      testType.set(TestSuiteType.UNIT_TEST)
    }
  }
}

tasks.check {
  dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport"))
}