package com.postnov.feedbacks.telegrambot

import com.postnov.feedbacks.telegrambot.processor.CallbackProcessor
import com.postnov.feedbacks.telegrambot.service.TelegramBotService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update


@Service
class TelegramBotServiceImpl(
    private val callbackProcessor: List<CallbackProcessor>
): TelegramBotService {
    override fun process(update: Update) =
        callbackProcessor.forEach { it.process(update) }
}