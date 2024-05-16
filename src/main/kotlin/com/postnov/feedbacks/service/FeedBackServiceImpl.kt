package com.postnov.feedbacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.postnov.feedbacks.dto.FeedbackDto
import com.postnov.feedbacks.service.client.WbClient
import com.postnov.feedbacks.service.itrf.FeedbackService
import com.postnov.feedbacks.utils.Constants.Companion.MAX_FEEDBACKS_FOR_STUDY
import com.postnov.feedbacks.utils.Constants.Companion.MAX_FEEDBACK_LENGTH
import com.postnov.feedbacks.utils.Constants.Companion.MAX_NEGATIVE_VALUATION
import com.postnov.feedbacks.utils.Constants.Companion.WB_FIRST_VERSION
import com.postnov.feedbacks.utils.Constants.Companion.WB_SECOND_VERSION
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.ZipException
import org.springframework.stereotype.Service

@Service
class FeedBackServiceImpl(
    private val objectMapper: ObjectMapper,
    private val wbClient: WbClient
): FeedbackService {
    override fun getNegativeFeedbacksByProductId(id: String, version: Int): List<String> {
        val allFeedbacks = getFeedbacksByProductId(id)
        val negativeFeedbacks = allFeedbacks.feedbacks
            ?.filter { it.productValuation != null && it.productValuation <= MAX_NEGATIVE_VALUATION && it.text != null }
            ?.map {
                if (it.text!!.length > MAX_FEEDBACK_LENGTH) {
                    it.text.subSequence(0, MAX_FEEDBACK_LENGTH).toString()
                } else { it.text }
            }
            ?: emptyList()
        return if (negativeFeedbacks.size > MAX_FEEDBACKS_FOR_STUDY) {
            val randomItems = calculateRandomItems(negativeFeedbacks.size)
            randomItems.map { negativeFeedbacks[it] }
        } else {
            negativeFeedbacks
        }
    }

    private fun calculateRandomItems(size: Int): Set<Int> {
        val result = mutableSetOf<Int>()
        while (result.size != MAX_FEEDBACKS_FOR_STUDY) {
            result.add((Math.random() * (size - 1)).toInt())
        }
        return result
    }

    private fun getFeedbacksByProductId(id: String, version: Int = WB_SECOND_VERSION): FeedbackDto {
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
                        feedbackDto
                    }
                } catch (ex: ZipException) {
                    val result = objectMapper.readValue(inByteArray, FeedbackDto::class.java)
                    if (result.feedbacks == null) {
                        getFeedbacksByProductId(id, WB_FIRST_VERSION)
                    } else {
                        result
                    }
                }
            }
        }
    }
}
