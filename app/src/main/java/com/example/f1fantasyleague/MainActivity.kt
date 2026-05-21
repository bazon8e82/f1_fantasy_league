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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.util.Log

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}