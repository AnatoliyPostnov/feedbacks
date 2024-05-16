package com.postnov.feedbacks.service.itrf

interface FeedbackService {
    fun getNegativeFeedbacksByProductId(id: String, version: Int = 2): List<String>
}