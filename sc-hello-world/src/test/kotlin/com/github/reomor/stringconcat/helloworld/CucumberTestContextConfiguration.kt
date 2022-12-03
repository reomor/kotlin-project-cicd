package com.github.reomor.stringconcat.helloworld

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

//@WebFluxTest
@SpringBootTest
@AutoConfigureWebTestClient
@CucumberContextConfiguration
open class CucumberTestContextConfiguration {
}
