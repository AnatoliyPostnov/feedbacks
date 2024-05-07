package com.postnov.feedbacks.telegrambot.processor

import org.telegram.telegrambots.meta.api.objects.Update

interface CallbackProcessor {
    fun process(update: Update)
    fun getChatId(update: Update): Long? =
        update.message?.chatId ?: update.callbackQuery?.message?.chatId
}