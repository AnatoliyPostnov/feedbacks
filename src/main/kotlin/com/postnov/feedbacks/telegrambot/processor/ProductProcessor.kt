package com.postnov.feedbacks.telegrambot.processor

import com.postnov.feedbacks.controller.service.GptAnswerService
import com.postnov.feedbacks.dto.ChatDto
import com.postnov.feedbacks.enums.UserState
import com.postnov.feedbacks.enums.UserState.REGISTERED
import com.postnov.feedbacks.telegrambot.sender.MessageSender
import com.postnov.feedbacks.utils.Constants.Companion.ERROR_PRODUCT_ID
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.Executors.newFixedThreadPool
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class ProductProcessor(
    private val userStatus: ConcurrentMap<Long, UserState>,
    private val messageSender: MessageSender,
    @Qualifier("gptLlamaApiService") private val gptAnswerService: GptAnswerService
): CallbackProcessor {
    private final val executorService = newFixedThreadPool(8)
    val blockingQueue = LinkedBlockingQueue<ChatDto>(5)
    val atomicInteger = AtomicInteger(0)

    init {
        executorService.submit { handleBlockingQueue() }
    }

    override fun process(update: Update) {
        val chatId = getChatId(update) ?: return
        if (userStatus[chatId] != REGISTERED) { return }
        val productId = update.message.text?.toIntOrNull()
        val response = productId?.let {
            val chat = ChatDto(chatId, productId)
            if (atomicInteger.getAndAdd(1) < 5) {
                blockingQueue.put(chat)
                "Отзывы для $it обрабатываются. После окончания обработки результат будет выведен на экран.\nВведите номер артикула: "
            } else {
                "Слишком много запросов в данный момент. Попробуйте чуть позже.\nВведите номер артикула: "
            }
        } ?: ERROR_PRODUCT_ID
        val message = SendMessage(
            chatId.toString(),
            response
        )
        messageSender.sendMessage(message)
    }

    private final fun handleBlockingQueue() {
        while (true) {
            if (atomicInteger.get() < 5) {
                val chatDto = blockingQueue.take()
                executorService.submit { handleProductId(chatDto) }
            }
        }
    }

    private final fun handleProductId(chat: ChatDto) {
        val result = gptAnswerService.getGptAnswer(chat.productId.toString())
        val message = SendMessage(
            chat.chatId.toString(),
            """
                    ----------------------------------------------------------
    Для артикула с номером ${chat.productId} был получен следующий результат: 
    $result
----------------------------------------------------------
Введите номер артикула:
                """.trimIndent()
        )
        atomicInteger.decrementAndGet()
        messageSender.sendMessage(message)
    }
}
