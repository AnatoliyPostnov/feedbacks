package com.postnov.feedbacks.dto

data class GptResponseDto(
    val messages: List<GptMessages>?
)

data class GptMessages(
    val type: String?,
    val content: String?
)
