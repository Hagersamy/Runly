package com.runly.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.runly.core.designsystem.components.RunlyFloatingButton
import com.runly.core.designsystem.components.RunlyScaffold
import com.runly.core.designsystem.components.RunlyToolbar
import com.runly.core.designsystem.util.DropDownItem
import com.runly.run.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunOverviewScreen(
    onAction: (RunOverViewActions) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RunlyScaffold(
        topAppBar = {
            RunlyToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runly),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = Icons.Default.Analytics,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = Icons.Default.Logout,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverViewActions.OnAnalyticsClick)
                        1 -> onAction(RunOverViewActions.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RunlyFloatingButton(
                icon = Icons.Default.DirectionsRun,
                onClick = {
                    onAction(RunOverViewActions.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}