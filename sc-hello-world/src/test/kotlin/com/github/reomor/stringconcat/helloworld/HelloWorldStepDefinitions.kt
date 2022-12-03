package com.github.reomor.stringconcat.helloworld

import com.github.reomor.stringconcat.helloworld.adapter.`in`.rest.Handlers
import io.cucumber.java.en.Then
import io.cucumber.java8.En
import org.springframework.test.web.reactive.server.WebTestClient

class HelloWorldStepDefinitions : En, SpringIntegrationTest() {

  init {
    When("the client calls {string} and status code is {int}") { endpoint: String, statusCode: Int ->
      executeGet("http://localhost:8080/$endpoint") {
        returnResult = expectStatus().isEqualTo(statusCode)
      }
    }
  }

  lateinit var returnResult: WebTestClient.ResponseSpec

  @Then("the client receives constant string")
  fun `should check the body`() {
    returnResult.expectBody(String::class.java)
      .isEqualTo(Handlers.MESSAGE)
  }
}