package com.postnov.feedbacks.controller.service

import com.postnov.feedbacks.dto.FeedbackDto

interface FeedBackService {
    fun getFeedbacksByProductId(id: String): List<String>
}