package com.runly.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.runly.core.designsystem.components.GradientBackground
import com.runly.auth.presentation.R
import com.runly.core.designsystem.Poppins
import com.runly.core.designsystem.RuniqueGray
import com.runly.core.designsystem.components.RunlyTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.ui.tooling.preview.Preview
import com.runly.auth.domain.UserDataValidator
import com.runly.auth.presentation.register.model.PasswordValidationState
import com.runly.core.designsystem.components.ActionButton


@Composable
fun RegisterScreen(
    registerState: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RuniqueGray
                    )
                ) {
                    append(
                        stringResource(
                            id = R.string.already_have_an_account
                        ) + " "
                    )
                }

                withLink(
                    LinkAnnotation.Clickable(
                        tag = "login",
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = Poppins
                            )
                        ),
                        linkInteractionListener = {
                            onAction(RegisterAction.OnLoginClick)
                        }
                    )
                ) {
                    append(stringResource(id = R.string.login))
                }
            }

            Text(
                text = annotatedString
            )

            Spacer(modifier = Modifier.height(48.dp))
            RunlyTextField(
                state = registerState.email,
                startIcon = Icons.Outlined.Email,
                endIcon = if (registerState.isEmailValid) {
                    Icons.Outlined.CheckCircle
                } else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            RunlyTextField(
                state = registerState.password,
                startIcon = Icons.Outlined.Lock,
                endIcon = null,
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = registerState.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number,
                ),
                isValid = registerState.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_char,
                ),
                isValid = registerState.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_char,
                ),
                isValid = registerState.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))
            ActionButton(
                text = stringResource(id = R.string.register),
                isLoading = registerState.isRegistering,
                enabled = registerState.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }

}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        registerState = RegisterState(
            passwordValidationState = PasswordValidationState(
                hasNumber = true,
            )
        ), {})
}