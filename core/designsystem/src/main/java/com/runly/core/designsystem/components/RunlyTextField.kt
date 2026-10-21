
package com.runly.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runly.core.designsystem.RuniqueTheme

@Composable
fun RunlyTextField(
    state: TextFieldState,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    hint: String,
    title: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
    isPassword: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val fieldShape = RoundedCornerShape(16.dp)

    val fieldBackground = if (isFocused) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val fieldBorder = if (isFocused) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (isPassword && !isPasswordVisible) {
            BasicSecureTextField(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(fieldShape)
                    .background(fieldBackground)
                    .border(1.dp, fieldBorder, fieldShape)
                    .padding(12.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(
                    MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        startIcon?.let { icon ->
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = hint,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            innerTextField()
                        }

                        IconButton(
                            onClick = {
                                isPasswordVisible = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Show password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        } else {
            TextField(
                state = state,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                placeholder = {
                    Text(text = hint)
                },
                leadingIcon = {
                    startIcon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                trailingIcon = {
                    if (isPassword) {
                        IconButton(
                            onClick = {
                                isPasswordVisible = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.VisibilityOff,
                                contentDescription = "Hide password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        endIcon?.let { icon ->
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) {
                        KeyboardType.Password
                    } else {
                        keyboardType
                    }
                ),
                lineLimits = TextFieldLineLimits.SingleLine,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(fieldShape)
                    .background(fieldBackground)
                    .border(1.dp, fieldBorder, fieldShape)
                    .padding(12.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RunlyTextFieldPreview() {
    RuniqueTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            RunlyTextField(
                state = rememberTextFieldState(),
                startIcon = Icons.Outlined.Email,
                endIcon = Icons.Outlined.CheckCircle,
                hint = "example@test.com",
                title = "Email",
                additionalInfo = "Must be a valid email",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            RunlyTextField(
                state = rememberTextFieldState(),
                startIcon = Icons.Outlined.Lock,
                endIcon = null,
                hint = "Enter your password",
                title = "Password",
                isPassword = true
            )
        }
    }
}
