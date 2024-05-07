package com.postnov.feedbacks.enums

enum class UserState {
    REGISTERED;

    companion object {
        fun fromString(data: String?): UserState? {
            if (data == null) { return null }
            return UserState.values().find { it.name == data.uppercase() }
                ?: throw RuntimeException("Не удалось определить тип UserState")
        }
    }
}