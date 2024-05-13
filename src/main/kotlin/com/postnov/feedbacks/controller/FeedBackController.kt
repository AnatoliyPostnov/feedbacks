package com.postnov.feedbacks.controller

import com.postnov.feedbacks.controller.service.GptAnswerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feedback")
@CrossOrigin
class FeedBackController(
    @Qualifier("gptLlamaApiService") val gptAnswerService: GptAnswerService
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/parse")
    fun getFeedbacksByProductId(@RequestParam id: String): ResponseEntity<String> {
        return ResponseEntity(gptAnswerService.getGptAnswer(id), HttpStatus.OK)
    }
}
