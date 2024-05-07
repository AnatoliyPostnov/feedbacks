package com.postnov.feedbacks.telegrambot.processor

import com.postnov.feedbacks.enums.UserState
import com.postnov.feedbacks.enums.UserState.REGISTERED
import com.postnov.feedbacks.telegrambot.sender.MessageSender
import com.postnov.feedbacks.utils.Constants.Companion.HELLO_MESSAGE
import java.util.concurrent.ConcurrentMap
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class HelloProcessor(
    private val userStatus: ConcurrentMap<Long, UserState>,
    private val messageSender: MessageSender
): CallbackProcessor {
    override fun process(update: Update) {
        val chatId = getChatId(update) ?: return
        if (userStatus[chatId] == REGISTERED) { return }
        val message = SendMessage(chatId.toString(), HELLO_MESSAGE)
        userStatus[chatId] = REGISTERED
        messageSender.sendMessage(message)
    }
}