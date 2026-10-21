package com.example.runly.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppKey : NavKey {
    @Serializable
    data object Auth : AppKey

    @Serializable
    data object Run : AppKey
}
