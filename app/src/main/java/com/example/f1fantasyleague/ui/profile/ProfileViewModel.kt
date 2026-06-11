package com.example.f1fantasyleague.ui.profile

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.FIELD_EMAIL
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_POINTS
import com.example.f1fantasyleague.data.firestore.FIELD_TITLES
import com.example.f1fantasyleague.data.firestore.FIELD_WINS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val points: Int = 0,
    val wins: Int = 0,
    val titles: Int = 0,
    val isLoading: Boolean = true,
    @StringRes val errorMessageResId: Int? = null
)

class ProfileViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser

                if (currentUser == null) {
                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        errorMessageResId = R.string.error_user_not_logged_in
                    )
                    return@launch
                }

                val document = firestore
                    .collection(COLLECTION_USERS)
                    .document(currentUser.uid)
                    .get()
                    .await()

                _uiState.value = ProfileUiState(
                    name = document.getString(FIELD_NAME).orEmpty(),
                    email = document.getString(FIELD_EMAIL) ?: currentUser.email.orEmpty(),
                    points = document.getLong(FIELD_POINTS)?.toInt() ?: 0,
                    wins = document.getLong(FIELD_WINS)?.toInt() ?: 0,
                    titles = document.getLong(FIELD_TITLES)?.toInt() ?: 0,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    errorMessageResId = R.string.error_loading_profile
                )
            }
        }
    }
}