package com.runly.auth.presentation.register

import com.runly.core.presentation.ui.error_handling.UiText

sealed interface RegisterEvent{
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}
