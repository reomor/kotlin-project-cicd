package com.github.reomor.stringconcat.helloworld

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.reflect.KClass

abstract class SpringIntegrationTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  open fun <T : Any> getBody(uri: String, bodyClass: KClass<T>): EntityExchangeResult<T> {
    return webTestClient.get()
      .uri(uri)
      .exchange()
      .expectStatus().is2xxSuccessful
      .expectBody(bodyClass.java)
      .returnResult()
  }

  open fun executeGet(uri: String, block: WebTestClient.ResponseSpec.() -> Unit) {
    webTestClient.get()
      .uri(uri)
      .exchange()
      .apply(block)
  }
}