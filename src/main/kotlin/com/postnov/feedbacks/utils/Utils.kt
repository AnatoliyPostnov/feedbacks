package com.postnov.feedbacks.utils

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

suspend inline fun <reified T> HttpResponse.tryReceive(): T? {
    return when (status) {
        HttpStatusCode.NotFound -> null
        HttpStatusCode.OK -> receive<T>()
        else -> throw RuntimeException("Failed to make request ${request.url}: HTTP $status")
    }
}
