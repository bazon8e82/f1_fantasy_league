package com.example.f1fantasyleague

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun LoginScreen(
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: (String, String, String) -> Unit
) {
    var isSignUpMode by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                text = if (isSignUpMode) "Create Account" else "Welcome",
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isSignUpMode) "Sign up to start playing" else "Sign in to continue",
                color = TextMuted,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isSignUpMode) {
                LoginTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Name"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            LoginTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = "Email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true
            )

            if (isSignUpMode) {
                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm password",
                    isPassword = true
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (isSignUpMode) {
                        if (password == confirmPassword) {
                            onSignUpClick(name.trim(), email.trim(), password)
                        }
                    } else {
                        onSignInClick(email.trim(), password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed,
                    contentColor = TextPrimary
                )
            ) {
                Text(if (isSignUpMode) "Sign Up" else "Sign In")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = if (isSignUpMode) "Already have an account? " else "Don't have an account? ",
                    color = TextMuted
                )

                Text(
                    text = if (isSignUpMode) "Sign In" else "Sign Up",
                    color = PrimaryRed,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        isSignUpMode = !isSignUpMode
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
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextMuted) },
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            androidx.compose.ui.text.input.VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle password",
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