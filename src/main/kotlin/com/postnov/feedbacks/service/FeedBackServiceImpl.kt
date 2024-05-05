package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.postnov.feedbacks.controller.service.FeedBackService
import com.postnov.feedbacks.dto.FeedbackDto
import com.postnov.feedbacks.service.client.WbClient
import org.springframework.stereotype.Service

@Service
class FeedBackServiceImpl(
    private val objectMapper: ObjectMapper,
    private val wbClient: WbClient
): FeedBackService {
    override fun parseFeedBack(inputData: FeedbackDto): List<String> {
        return inputData.feedbacks?.mapNotNull { it.text }
            ?: throw RuntimeException("Parsing result can`t be null")
    }

    override fun getFeedbacksByProductId(id: String): List<String> {
        val product = wbClient.getProductByProductId(id)
        val cardId = product.data?.products?.firstOrNull()?.root
            ?: throw RuntimeException("cardId can`t be null")
        val feedBacks = wbClient.getFeedbackByCardId(cardId).feedbacks ?: emptyList()
        return feedBacks.mapNotNull { it.text }
    }
}