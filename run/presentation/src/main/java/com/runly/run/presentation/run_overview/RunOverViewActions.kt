package com.runly.run.presentation.run_overview

sealed interface RunOverViewActions {
    data object OnStartClick: RunOverViewActions
    data object OnLogoutClick: RunOverViewActions
    data object OnAnalyticsClick: RunOverViewActions
}