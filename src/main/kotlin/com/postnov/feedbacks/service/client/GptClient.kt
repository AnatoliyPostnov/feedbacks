package com.postnov.feedbacks.service.client

import com.postnov.feedbacks.dto.GptRequestDto
import com.postnov.feedbacks.dto.GptResponseDto

interface GptClient {
    fun getResponseFromGpt(gptRequestDto: GptRequestDto): GptResponseDto
}
