package com.example.runly.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.runly.auth.presentation.navigation.AuthRoot

@Composable
fun AppRoot(
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(if (isLoggedIn) AppKey.Run else AppKey.Auth)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppKey.Auth> {
                AuthRoot(
                    onAuthSuccess = {
                        backStack.clear()
                        backStack.add(AppKey.Run)
                    }
                )
            }
            entry<AppKey.Run> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Run screen")
                }
            }
        }
    )
}
