package com.postnov.feedbacks.dto

data class GptRequestDto(
    val conversationId: String? = "1",
    val botId: String? = "7351009187687661574",
    val user: String? = "7286588006302",
    val stream: Boolean? = false,
    val query: String?
)
