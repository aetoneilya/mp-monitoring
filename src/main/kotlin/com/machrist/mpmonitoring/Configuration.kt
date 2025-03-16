package com.machrist.mpmonitoring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {
    @Bean("tgBotClient")
    fun getBotClient(): WebClient {
        return WebClient.builder().baseUrl("").build()
    }
}
