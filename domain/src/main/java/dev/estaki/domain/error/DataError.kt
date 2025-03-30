package dev.estaki.domain.error

sealed interface DataError: Error {
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION
    }

    enum class Local: DataError{
        DISK_FULL,
        OTHERS
    }

    enum class ContentProvider: DataError{
        PERMISSION_DENIED,
        SMS_PROVIDER_ERROR,
        CURSOR_ERROR,
        INVALID_SMS_FORMAT,
        UNKNOWN_ERROR
    }

}