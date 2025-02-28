package dev.estaki.domain.error

sealed interface Error

enum class SmsReadError:Error {
    PERMISSION_DENIED,
    SMS_PROVIDER_ERROR,
    CURSOR_ERROR,
    INVALID_SMS_FORMAT,
    UNKNOWN_ERROR
}
