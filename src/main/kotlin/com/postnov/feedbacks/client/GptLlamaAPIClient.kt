package com.postnov.feedbacks.client

import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.dto.GptResponseDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.utils.tryReceive
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class GptLlamaAPIClient(
    private val httpClient: HttpClient
): GptClient {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    override fun getResponseFromGpt(gptRequestDto: GptRequestDto): GptResponseDto {
        return runBlocking {
            try {
                val response: HttpResponse = httpClient.post("http://81.163.25.109:8081/api/generate") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    body = gptRequestDto
                }
                response.tryReceive<GptResponseDto>() ?: throw RuntimeException("Unable to get result by gptRequestDto: $gptRequestDto.")
            } catch (ex: Exception) {
                log.error("Unable to get result by gptRequestDto: $gptRequestDto.", ex)
                throw RuntimeException("Unable to get result by gptRequestDto: $gptRequestDto.")
            }
        }
    }
}
