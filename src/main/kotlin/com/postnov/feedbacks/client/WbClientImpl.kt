package com.postnov.feedbacks.client

import com.postnov.feedbacks.dto.ProductDto
import com.postnov.feedbacks.service.client.WbClient
import com.postnov.feedbacks.utils.tryReceive
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class WbClientImpl(
    private val httpClient: HttpClient
): WbClient {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getProductByProductId(id: String): ProductDto =
        runBlocking {
            return@runBlocking try {
                val response: HttpResponse = httpClient.get("https://card.wb.ru/cards/v2/detail?nm=$id")
                response.tryReceive<ProductDto>() ?: throw RuntimeException("Unable to get result by ProductId: $id.")
            } catch (ex: Exception) {
                log.error("Unable to get result by ProductId: $id.", ex)
                throw RuntimeException("Unable to get result by ProductId: $id.")
            }
    }

    override fun getFeedbackByCardId(id: Int, version: Int): ByteArray =
        runBlocking {
            return@runBlocking try {
                val response: HttpResponse = httpClient.get("https://feedbacks$version.wb.ru/feedbacks/v1/$id")
                response.receive<ByteArray>()
            } catch (ex: Exception) {
                log.error("Unable to get result by CardId: $id.", ex)
                throw RuntimeException("Unable to get result by CardId: $id.")
            }
        }
}
