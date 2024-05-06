package com.postnov.feedbacks.service.client

interface GptClient {
    fun getResponseFromGpt(id: String): String
}
