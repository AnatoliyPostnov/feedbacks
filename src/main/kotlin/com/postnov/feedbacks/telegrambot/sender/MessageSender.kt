package com.postnov.feedbacks.telegrambot.sender

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface MessageSender {
    fun sendMessage(sendMessage: SendMessage)
}