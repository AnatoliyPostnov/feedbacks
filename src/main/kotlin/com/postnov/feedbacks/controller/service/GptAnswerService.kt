package com.postnov.feedbacks.controller.service

interface GptAnswerService {
    fun getGptAnswer(id: String): String
}