package com.runly.auth.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.runly.auth.presentation.R
import com.runly.auth.presentation.register.RegisterAction
import com.runly.auth.presentation.register.RegisterEvent
import com.runly.auth.presentation.register.RegisterScreen
import com.runly.auth.presentation.register.RegisterViewModel
import com.runly.core.presentation.ui.one_time_events.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_LONG).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        registerState = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> onSignInClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}
