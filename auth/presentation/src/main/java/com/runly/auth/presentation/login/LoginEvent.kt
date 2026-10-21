package com.runly.auth.presentation.login

import com.runly.core.presentation.ui.error_handling.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess: LoginEvent
}