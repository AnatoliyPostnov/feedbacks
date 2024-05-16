package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.postnov.feedbacks.controller.service.GptAnswerService
import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.service.itrf.FeedbackService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class GptLlamaApiService(
    @Qualifier("gptLlamaAPIClient") private val gptClient: GptClient,
    private val feedbackService: FeedbackService,
    private val objectMapper: ObjectMapper
): GptAnswerService {
    override fun getGptAnswer(id: String): String {
        val feedback = feedbackService.getNegativeFeedbacksByProductId(id)
        val feedbackObjectNode = objectMapper.createObjectNode().arrayNode()
        feedback.forEach { feedbackObjectNode.add(it) }
        val mainMessage = "Представь краткую выжимку отрицательных отзывов из текста ниже на русском языке."
        val query = "$mainMessage: $feedbackObjectNode"
        val gptRequestDto = GptRequestDto(prompt = query)
        val gptAnswer = gptClient.getResponseFromGpt(gptRequestDto)
        return gptAnswer.response ?: ""
    }
}
