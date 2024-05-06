package com.postnov.feedbacks.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.http.HttpHeaders.ContentEncoding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KtorConfig {
    val timeoutMills: Long = 60000
    @Bean
    fun ktorHttpClient(jacksonMapper: ObjectMapper): HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer(jacksonMapper)
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
