package com.example.f1fantasyleague

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.f1fantasyleague.ui.appnavigation.App
import com.example.f1fantasyleague.ui.login.LoginScreen
import com.example.f1fantasyleague.ui.login.LoginViewModel
import com.example.f1fantasyleague.ui.theme.F1FantasyLeagueTheme
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()

        setContent {
            F1FantasyLeagueTheme {
                val uiState by loginViewModel.uiState.collectAsState()

                LaunchedEffect(uiState.messageResId) {
                    uiState.messageResId?.let { messageResId ->
                        Toast.makeText(
                            this@MainActivity,
                            getString(messageResId),
                            Toast.LENGTH_SHORT
                        ).show()
                        loginViewModel.clearMessage()
                    }
                }

                if (uiState.isLoggedIn) {
                    App(
                        onLogout = {
                            loginViewModel.logout()
                        }
                    )
                } else {
                    LoginScreen(
                        uiState = uiState,
                        onNameChange = loginViewModel::onNameChange,
                        onEmailChange = loginViewModel::onEmailChange,
                        onPasswordChange = loginViewModel::onPasswordChange,
                        onConfirmPasswordChange = loginViewModel::onConfirmPasswordChange,
                        onToggleSignUpMode = loginViewModel::toggleSignUpMode,
                        onSignInClick = loginViewModel::signIn,
                        onSignUpClick = loginViewModel::signUp
                    )
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                requestNotificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}