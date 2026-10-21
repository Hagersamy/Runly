package com.runly.auth.presentation.navigation

import androidx.compose.runtime.Composable
import com.runly.auth.presentation.intro.IntroAction
import com.runly.auth.presentation.intro.IntroScreen

@Composable
fun IntroScreenRoot(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        }
    )
}
