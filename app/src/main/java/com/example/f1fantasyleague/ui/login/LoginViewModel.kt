package com.example.f1fantasyleague.ui.login

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isSignUpMode: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    @StringRes val messageResId: Int? = null
)

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authState.collect { isLoggedIn ->
                _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email.trim())
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun toggleSignUpMode() {
        _uiState.value = _uiState.value.copy(
            isSignUpMode = !_uiState.value.isSignUpMode,
            name = "",
            password = "",
            confirmPassword = "",
            messageResId = null
        )
    }

    fun signIn() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(messageResId = R.string.msg_enter_credentials)
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, messageResId = null)

            val result = authRepository.signIn(state.email, state.password)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                messageResId = result.fold(
                    onSuccess = { R.string.msg_sign_in_success },
                    onFailure = { R.string.msg_login_error }
                )
            )
        }
    }

    fun signUp() {
        val state = _uiState.value

        if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(messageResId = R.string.msg_fill_fields)
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.value = state.copy(messageResId = R.string.msg_password_mismatch)
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, messageResId = null)

            val result = authRepository.signUp(state.name, state.email, state.password)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                messageResId = result.fold(
                    onSuccess = { R.string.msg_account_created },
                    onFailure = { R.string.msg_login_error }
                )
            )
        }
    }

    fun logout() {
        authRepository.signOut()
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(messageResId = null)
    }
}