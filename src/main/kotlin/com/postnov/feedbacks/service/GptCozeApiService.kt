package com.postnov.feedbacks.service

import com.postnov.feedbacks.controller.service.GptAnswerService
import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.service.itrf.FeedbackService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class GptCozeApiService(
    @Qualifier("gptCozeAPIClient") private val gptClient: GptClient,
    private val feedbackService: FeedbackService
): GptAnswerService {
    override fun getGptAnswer(id: String, conversationId: String): String {
        val feedback = feedbackService.getFeedbacksByProductId(id)
        val mainMessage = "Сделай краткую выжимку чаще всего встречающихся негативных отзывов из списка ниже."
        val prepareFeedBacks = if (feedback.size > 300) { feedback.subList(0, 300) } else { feedback }
        val gptRequestDto = GptRequestDto(conversationId = conversationId, query = "$mainMessage[${prepareFeedBacks.joinToString("; ")}]", prompt = "")
        val gptAnswer = gptClient.getResponseFromGpt(gptRequestDto)
        return gptAnswer.messages?.find { it.type == "answer" }?.content ?: ""
    }
}
