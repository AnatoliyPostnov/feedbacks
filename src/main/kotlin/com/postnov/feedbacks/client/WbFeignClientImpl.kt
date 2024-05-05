package com.postnov.feedbacks.client

import com.postnov.feedbacks.dto.FeedbackDto
import com.postnov.feedbacks.dto.ProductDto
import com.postnov.feedbacks.service.client.WbClient
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class WbFeignClientImpl(
    private val httpClient: HttpClient
): WbClient {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getProductByProductId(id: String): ProductDto {
TODO()
    }

    override fun getFeedbackByCardId(id: Int, version: Int): FeedbackDto {
TODO()
    }
}

