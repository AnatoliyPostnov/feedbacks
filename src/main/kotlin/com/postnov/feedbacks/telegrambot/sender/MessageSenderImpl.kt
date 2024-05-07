package com.postnov.feedbacks.telegrambot.sender

import com.postnov.feedbacks.telegrambot.TelegramBot
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MessageSenderImpl(
    private val telegramBot: TelegramBot
): MessageSender {
    override fun sendMessage(sendMessage: SendMessage) {
        telegramBot.sendAnswerMessage(sendMessage)
    }
}