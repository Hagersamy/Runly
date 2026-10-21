package com.runly.auth.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthKey : NavKey {
    @Serializable
    data object IntroScreen : AuthKey

    @Serializable
    data object LoginScreen : AuthKey

    @Serializable
    data object RegisterScreen : AuthKey
}
