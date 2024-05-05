package com.postnov.feedbacks.controller.service

import com.postnov.feedbacks.dto.FeedbackDto

interface FeedBackService {
    fun parseFeedBack(inputData: FeedbackDto): List<String>

    fun getFeedbacksByProductId(id: String): List<String>
}