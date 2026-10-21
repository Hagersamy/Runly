package com.runly.auth.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.runly.auth.presentation.login.LoginAction
import com.runly.auth.presentation.login.LoginEvent
import com.runly.auth.presentation.login.LoginScreen
import com.runly.auth.presentation.login.LoginViewModel
import com.runly.core.presentation.ui.one_time_events.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            LoginEvent.LoginSuccess -> onLoginSuccess()
        }
    }

    LoginScreen(
        state = viewModel.state,
        loginAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}
