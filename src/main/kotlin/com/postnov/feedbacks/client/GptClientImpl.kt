package com.postnov.feedbacks.client

import com.postnov.feedbacks.dto.ProductDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.utils.tryReceive
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class GptClientImpl(
    private val httpClient: HttpClient
): GptClient {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    override fun getResponseFromGpt(id: String): String {
        return runBlocking {
            try {
                val response: HttpResponse = httpClient.post("https://api.coze.com/open_api/v2/chat")
                response.tryReceive<String>() ?: throw RuntimeException("Unable to get result by ProductId: $id.")
            } catch (ex: Exception) {
                log.error("Unable to get result by ProductId: $id.", ex)
                throw RuntimeException("Unable to get result by ProductId: $id.")
            }
        }
    }
}
