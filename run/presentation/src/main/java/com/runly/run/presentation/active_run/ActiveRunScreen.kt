package com.runly.run.presentation.active_run

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.runly.core.designsystem.components.ActionButton
import com.runly.core.designsystem.components.RunlyDialog
import com.runly.core.designsystem.components.RunlyFloatingButton
import com.runly.core.designsystem.components.RunlyScaffold
import com.runly.core.designsystem.components.RunlyToolbar
import com.runly.run.presentation.R
import com.runly.run.presentation.active_run.componenets.RunDataCard
import android.Manifest
import com.runly.run.presentation.active_run.utils.hasLocationPermission
import com.runly.run.presentation.active_run.utils.hasNotificationPermission
import com.runly.run.presentation.active_run.utils.requestRunlyPermissions
import com.runly.run.presentation.active_run.utils.shouldShowLocationPermissionRationale
import com.runly.run.presentation.active_run.utils.shouldShowNotificationPermissionRationale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunActions) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunActions.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunActions.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
    }
    LaunchedEffect(key1 = Unit) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunActions.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunActions.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRunlyPermissions(context)
        }
    }
    RunlyScaffold(
        withGradient = false,
        topAppBar = {
            RunlyToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunActions.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RunlyFloatingButton(
                icon = if (state.shouldTrack) {
                    Icons.Default.Pause
//                    StopIcon
                } else {
                    Icons.Default.PlayArrow
//                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunActions.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }
    if (state.showLocationRationale && state.showNotificationRationale) {
        RunlyDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            firstButton = {
                ActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunActions.DismissRationaleDialog)
                        permissionLauncher.requestRunlyPermissions(context)
                    }
                )
            }
        )
    }
}