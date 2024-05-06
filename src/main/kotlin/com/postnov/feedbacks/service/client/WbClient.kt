package com.postnov.feedbacks.service.client

import com.postnov.feedbacks.dto.ProductDto

interface WbClient {
    fun getProductByProductId(id: String): ProductDto
    fun getFeedbackByCardId(id: Int, version: Int = 2): ByteArray
}
