package com.postnov.feedbacks.dto

data class FeedbackDto(
    val feedbacks: List<Feedbacks>?
)

class Feedbacks(
    val text: String?
)
