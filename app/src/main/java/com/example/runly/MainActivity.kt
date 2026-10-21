package com.example.runly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.runly.navigation.AppRoot
import com.example.runly.ui.theme.RunlyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunlyTheme {
                if (!viewModel.state.isCheckingAuth) {
                    AppRoot(isLoggedIn = viewModel.state.isLoggedIn)
                }
            }
        }
    }
}
