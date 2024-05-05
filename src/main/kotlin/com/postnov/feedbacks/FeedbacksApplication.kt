package com.postnov.feedbacks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FeedbacksApplication

fun main(args: Array<String>) {
	runApplication<FeedbacksApplication>(*args)
}
