package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.postnov.feedbacks.dto.FeedbackDto
import com.postnov.feedbacks.service.client.GptClient
import com.postnov.feedbacks.service.client.WbClient
import com.postnov.feedbacks.service.itrf.FeedbackService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.ZipException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FeedBackServiceImpl(
    private val objectMapper: ObjectMapper,
    private val wbClient: WbClient
): FeedbackService {
    override fun getFeedbacksByProductId(id: String, version: Int): List<String> {
        val product = wbClient.getProductByProductId(id)
        val cardId = product.data?.products?.firstOrNull()?.root
            ?: throw RuntimeException("cardId can`t be null")
        val inByteArray = wbClient.getFeedbackByCardId(cardId, version = version)
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ByteArrayInputStream(inByteArray).use { byteArrayInputStream ->
                return try {
                    GZIPInputStream(byteArrayInputStream).use { gzipInputStream ->
                        gzipInputStream.transferTo(byteArrayOutputStream)
                        val content = byteArrayOutputStream.toByteArray()
                        val feedbackDto = objectMapper.readValue(content, FeedbackDto::class.java)
                        parseFeedBack(feedbackDto)
                    }
                } catch (ex: ZipException) {
                    val result = objectMapper.readValue(inByteArray, FeedbackDto::class.java)
                    if (result.feedbacks == null) {
                        getFeedbacksByProductId(id, 1)
                    } else {
                        parseFeedBack(result)
                    }
                }
            }
        }
    }

    private fun parseFeedBack(inputData: FeedbackDto): List<String> {
        return inputData.feedbacks?.mapNotNull { it.text }?.filter { it.isNotEmpty() }
            ?: throw RuntimeException("Parsing result can`t be null")
    }
}
