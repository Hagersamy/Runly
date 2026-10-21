package com.runly.auth.presentation.register.mapper

import com.runly.auth.domain.models.PasswordValidationState
import com.runly.auth.presentation.register.model.PasswordValidationState as UiPasswordValidationState

fun PasswordValidationState.toUi(): UiPasswordValidationState {
    return UiPasswordValidationState(
        hasMinLength = hasMinLength,
        hasNumber = hasNumber,
        hasLowerCaseCharacter = hasLowerCaseCharacter,
        hasUpperCaseCharacter = hasUpperCaseCharacter
    )
}