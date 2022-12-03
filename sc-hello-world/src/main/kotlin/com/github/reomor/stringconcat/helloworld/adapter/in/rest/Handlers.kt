package com.github.reomor.stringconcat.helloworld.adapter.`in`.rest

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class Handlers {

  companion object {
    const val MESSAGE = "Hello world"
  }

  suspend fun pull(request: ServerRequest): ServerResponse {

    return ServerResponse.ok()
      .bodyValue(MESSAGE)
      .awaitSingle()
  }
}