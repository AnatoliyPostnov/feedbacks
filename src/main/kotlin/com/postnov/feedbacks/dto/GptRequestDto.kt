package com.postnov.feedbacks.dto

data class GptRequestDto(
    val stream: Boolean? = false,
    val model: String? = "llama3",
    val prompt: String?,
)

