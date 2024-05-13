package com.postnov.feedbacks.dto

data class GptResponseDto(
    val messages: List<GptMessages>?,
    val response: String?
)

data class GptMessages(
    val type: String?,
    val content: String?
)
