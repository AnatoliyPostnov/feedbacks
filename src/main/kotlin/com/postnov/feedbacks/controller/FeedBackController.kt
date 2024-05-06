package com.postnov.feedbacks.controller

import com.fasterxml.jackson.databind.node.ObjectNode
import com.postnov.feedbacks.controller.service.FeedBackService
import com.postnov.feedbacks.dto.FeedbackDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feedback")
@CrossOrigin
class FeedBackController(
    val feedBackService: FeedBackService
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/parse")
    fun getFeedbacksByProductId(@RequestParam id: String): ResponseEntity<List<String>> {
        return ResponseEntity(feedBackService.getFeedbacksByProductId(id), HttpStatus.OK)
    }
}
