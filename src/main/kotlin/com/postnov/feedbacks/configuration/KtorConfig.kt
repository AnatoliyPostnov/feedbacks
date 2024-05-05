package com.postnov.feedbacks.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.JacksonConverter
import io.ktor.serialization.jackson.jackson
import org.slf4j.bridge.SLF4JBridgeHandler.install
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KtorConfig {
    val timeoutMills: Long = 60000
    @Bean
    fun ktorHttpClient(jacksonMapper: ObjectMapper): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson {
                register(Json, JacksonConverter(jacksonMapper))
            }
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = timeoutMills
            connectTimeoutMillis = 3000
            socketTimeoutMillis = timeoutMills
        }
    }
}
