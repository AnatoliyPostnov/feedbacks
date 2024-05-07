package com.postnov.feedbacks.telegrambot.service

import org.telegram.telegrambots.meta.api.objects.Update

interface TelegramBotService {
    fun process(update: Update)
}