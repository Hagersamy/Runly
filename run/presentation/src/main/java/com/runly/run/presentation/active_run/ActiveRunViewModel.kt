package com.runly.run.presentation.active_run

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel(): ViewModel() {
    var state = mutableStateOf(ActiveRunState())
        private set // that viewmodel only can edit or set data the other ui can get state only
    private val eventChannel = Channel<ActiveRunEvent>()
    val event = eventChannel.receiveAsFlow()
    private val _hasLocationPermission = MutableStateFlow(false)
    init{

    }

}