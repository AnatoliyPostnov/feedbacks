package com.postnov.feedbacks.client

import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.dto.GptResponseDto
import com.postnov.feedbacks.dto.ProductDto
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
class GptClientImpl(
    private val httpClient: HttpClient
): GptClient {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    override fun getResponseFromGpt(gptRequestDto: GptRequestDto): GptResponseDto {
        return runBlocking {
            try {
                val response: HttpResponse = httpClient.post("https://api.coze.com/open_api/v2/chat") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    header("Authorization", "Bearer pat_tSBCGwo1PJEmMsHYnZlufkP18mM106lf1On4ViPnfRnuiUeVTfdP9t1KM1JOBgMS")
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
