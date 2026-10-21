package com.runly.core.domain.utils

sealed interface DataError: Error {
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Local: DataError{
        NOT_FOUND,
        CONFLICT,
        DISK_FULL,
        UNKNOWN
    }
}