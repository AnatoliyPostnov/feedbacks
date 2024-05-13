package com.postnov.feedbacks.controller.service

interface GptAnswerService {
    fun getGptAnswer(id: String, conversationId: String = "1"): String
}