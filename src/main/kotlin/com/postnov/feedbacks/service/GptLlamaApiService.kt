package com.postnov.feedbacks.service

import com.postnov.feedbacks.controller.service.GptAnswerService
import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.service.itrf.FeedbackService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class GptLlamaApiService(
    @Qualifier("gptLlamaAPIClient") private val gptClient: GptClient,
    private val feedbackService: FeedbackService
): GptAnswerService {
    override fun getGptAnswer(id: String, conversationId: String): String {
        val feedback = feedbackService.getFeedbacksByProductId(id)
        val mainMessage = "Представь краткую выжимку отрицательных отзывов из текста ниже на русском языке."
        val prepareFeedBacks = if (feedback.size > 300) { feedback.subList(0, 300) } else { feedback }
        val query = "$mainMessage[${prepareFeedBacks.joinToString("; ").subSequence(0, 4000)}]"
        val gptRequestDto = GptRequestDto(conversationId = conversationId, query = query, prompt = query)
        val gptAnswer = gptClient.getResponseFromGpt(gptRequestDto)
        return gptAnswer.response ?: ""
    }
}
