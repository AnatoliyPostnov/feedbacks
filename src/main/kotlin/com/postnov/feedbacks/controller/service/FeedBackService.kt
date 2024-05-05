package com.postnov.feedbacks.controller.service

import com.fasterxml.jackson.databind.node.ObjectNode

interface FeedBackService {
    fun parseFeedBack(inputData: String): List<String>
}