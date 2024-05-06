package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.postnov.feedbacks.controller.service.FeedBackService
import com.postnov.feedbacks.dto.FeedbackDto
import com.postnov.feedbacks.service.client.WbClient
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FeedBackServiceImpl(
    private val objectMapper: ObjectMapper,
    @Qualifier("wbClientImpl") private val wbClient: WbClient
): FeedBackService {
    override fun getFeedbacksByProductId(id: String): List<String> {
        val product = wbClient.getProductByProductId(id)
        val cardId = product.data?.products?.firstOrNull()?.root
            ?: throw RuntimeException("cardId can`t be null")
        val inByteArray = wbClient.getFeedbackByCardId(cardId)
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ByteArrayInputStream(inByteArray).use { byteArrayInputStream ->
                GZIPInputStream(byteArrayInputStream).use { gzipInputStream ->
                    gzipInputStream.transferTo(byteArrayOutputStream)
                    val content = byteArrayOutputStream.toByteArray()
                    val feedBacks = objectMapper.readValue(content, FeedbackDto::class.java)
                    return parseFeedBack(feedBacks)
                }
            }
        }
    }

    private fun parseFeedBack(inputData: FeedbackDto): List<String> {
        return inputData.feedbacks?.mapNotNull { it.text }
            ?: throw RuntimeException("Parsing result can`t be null")
    }
}
