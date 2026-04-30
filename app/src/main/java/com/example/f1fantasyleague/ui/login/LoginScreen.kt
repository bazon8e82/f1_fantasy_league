package com.example.f1fantasyleague.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.f1fantasyleague.R

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onToggleSignUpMode: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (uiState.isSignUpMode)
                    stringResource(R.string.login_create_account)
                else
                    stringResource(R.string.login_welcome),
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (uiState.isSignUpMode)
                    stringResource(R.string.login_signup_subtitle)
                else
                    stringResource(R.string.login_signin_subtitle),
                color = TextMuted,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isSignUpMode) {
                LoginTextField(
                    value = uiState.name,
                    onValueChange = onNameChange,
                    label = stringResource(R.string.login_name)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            LoginTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = stringResource(R.string.login_email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.login_password),
                isPassword = true
            )

            if (uiState.isSignUpMode) {
                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = stringResource(R.string.login_confirm_password),
                    isPassword = true
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (uiState.isSignUpMode) {
                        onSignUpClick()
                    } else {
                        onSignInClick()
                    }
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed,
                    contentColor = TextPrimary
                )
            ) {
                Text(
                    text = if (uiState.isSignUpMode)
                        stringResource(R.string.login_sign_up)
                    else
                        stringResource(R.string.login_sign_in)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = if (uiState.isSignUpMode)
                        stringResource(R.string.login_already_have_account)
                    else
                        stringResource(R.string.login_dont_have_account),
                    color = TextMuted
                )

                Text(
                    text = if (uiState.isSignUpMode)
                        stringResource(R.string.login_sign_in)
                    else
                        stringResource(R.string.login_sign_up),
                    color = PrimaryRed,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onToggleSignUpMode()
                    }
                )
            }
        }
    }
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextMuted) },
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.toggle_password_desc),
                        tint = IconMuted
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = PrimaryRed,
            unfocusedBorderColor = BorderDark,
            focusedLabelColor = PrimaryRed,
            unfocusedLabelColor = TextMuted,
            cursorColor = PrimaryRed
        )
    )
}