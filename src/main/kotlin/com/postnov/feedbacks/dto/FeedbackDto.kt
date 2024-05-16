package com.postnov.feedbacks.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FeedbackDto(
    val feedbacks: List<Feedbacks>?
)

class Feedbacks(
    val text: String?,
    @JsonProperty("productValuation")
    val productValuation: Int?
)
