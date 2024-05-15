package com.postnov.feedbacks.telegrambot

import javax.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update


/*
* This class is used to create a connection with a telegram bot and all.
* */
@Component
class TelegramBot(
    @Value("\${telegram.name}")
    private val botName: String,
    @Value("\${telegram.uri}")
    private val botUri: String,
    @Value("\${telegram.token}")
    private val token: String,
) : TelegramWebhookBot() {
    @PostConstruct
    fun init() { this.setWebhook(SetWebhook.builder().url(botUri).build()) }
    override fun getBotToken() = token

    override fun getBotUsername() = botName

    override fun getBotPath() = "/update"

    fun sendAnswerMessage(message: SendMessage?) {
        message?.let { execute(message) }
    }

    override fun onWebhookUpdateReceived(update: Update?): BotApiMethod<*> {
        //Этот метод не понадобится
        TODO("Not yet implemented")
    }
}