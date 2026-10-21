package com.runly.run.presentation.active_run

import com.runly.core.presentation.ui.error_handling.UiText

sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}
