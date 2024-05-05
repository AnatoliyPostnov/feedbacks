package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.postnov.feedbacks.controller.service.FeedBackService
import org.springframework.stereotype.Service

@Service
class FeedBackServiceImpl(
    private val objectMapper: ObjectMapper
): FeedBackService {
    override fun parseFeedBack(inputData: String): List<String> {
        return ((objectMapper.readTree(inputData) as? ObjectNode)
            ?.get("feedbacks") as? ArrayNode)
            ?.mapNotNull { (it as? ObjectNode) }
            ?.mapNotNull { it.get("text").textValue() }
            ?: throw RuntimeException("Parsing result can`t be null")
    }
}