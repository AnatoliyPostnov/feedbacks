package com.postnov.feedbacks.service.itrf

interface FeedbackService {
    fun getFeedbacksByProductId(id: String, version: Int = 2): List<String>
}