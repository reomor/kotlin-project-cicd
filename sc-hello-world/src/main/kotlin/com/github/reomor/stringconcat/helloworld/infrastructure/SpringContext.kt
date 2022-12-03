package com.github.reomor.stringconcat.helloworld.infrastructure

import com.github.reomor.stringconcat.helloworld.adapter.`in`.rest.Handlers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class SpringContext {

  @Bean
  open fun routes(handlers: Handlers) = coRouter {
    GET("/hello", handlers::pull)
  }
}
