package com.postnov.feedbacks.telegrambot

import com.postnov.feedbacks.telegrambot.service.TelegramBotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.objects.Update

@RestController
class TelegramBotController(
    private val telegramBotService: TelegramBotService
) {
    @PostMapping("/callback/update")
    fun onUpdateReceived(@RequestBody update: Update): ResponseEntity<Any> {
        telegramBotService.process(update)
        return ResponseEntity(HttpStatus.OK)
    }
}