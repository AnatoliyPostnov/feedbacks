package com.postnov.feedbacks.configuration

import com.postnov.feedbacks.enums.UserState
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserStatusConfiguration {

    @Bean
    fun userStatus(): ConcurrentMap<Long, UserState> {
        return ConcurrentHashMap()
    }
}
