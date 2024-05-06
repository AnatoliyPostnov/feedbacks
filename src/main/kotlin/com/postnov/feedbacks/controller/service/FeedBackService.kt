package com.postnov.feedbacks.controller.service

interface FeedBackService {
    fun getGptAnswer(id: String): String
}