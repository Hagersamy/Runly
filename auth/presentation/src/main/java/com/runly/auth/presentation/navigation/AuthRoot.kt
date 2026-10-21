package com.runly.auth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun AuthRoot(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(AuthKey.IntroScreen)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AuthKey.IntroScreen> {
                IntroScreenRoot(
                    onSignInClick = { backStack.add(AuthKey.LoginScreen) },
                    onSignUpClick = { backStack.add(AuthKey.RegisterScreen) }
                )
            }
            entry<AuthKey.RegisterScreen> {
                RegisterScreenRoot(
                    onSignInClick = {
                        backStack.removeLastOrNull()
                        backStack.add(AuthKey.LoginScreen)
                    },
                    onSuccessfulRegistration = {
                        backStack.removeLastOrNull()
                        backStack.add(AuthKey.LoginScreen)
                    }
                )
            }
            entry<AuthKey.LoginScreen> {
                LoginScreenRoot(
                    onLoginSuccess = onAuthSuccess,
                    onSignUpClick = {
                        backStack.removeLastOrNull()
                        backStack.add(AuthKey.RegisterScreen)
                    }
                )
            }
        }
    )
}
