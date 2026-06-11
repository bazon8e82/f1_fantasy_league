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
    val bestWeekendScore: Int = 0,
    val avatarId: Int = 0,
    val isLoading: Boolean = true,
    val isSavingName: Boolean = false,
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

                val userDocumentRef = firestore
                    .collection(COLLECTION_USERS)
                    .document(currentUser.uid)

                val document = userDocumentRef
                    .get()
                    .await()

                val scoresSnapshot = userDocumentRef
                    .collection("scores")
                    .get()
                    .await()

                val bestWeekendScore = scoresSnapshot.documents.maxOfOrNull { scoreDocument ->
                    scoreDocument.getLong("totalPoints")?.toInt() ?: 0
                } ?: 0

                _uiState.value = ProfileUiState(
                    name = document.getString(FIELD_NAME).orEmpty(),
                    email = document.getString(FIELD_EMAIL) ?: currentUser.email.orEmpty(),
                    points = document.getLong(FIELD_POINTS)?.toInt() ?: 0,
                    wins = document.getLong(FIELD_WINS)?.toInt() ?: 0,
                    titles = document.getLong(FIELD_TITLES)?.toInt() ?: 0,
                    bestWeekendScore = bestWeekendScore,
                    avatarId = document.getLong("avatarId")?.toInt() ?:0,
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

    fun updateName(newName: String) {
        val trimmedName = newName.trim()

        if (trimmedName.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser

                if (currentUser == null) {
                    _uiState.value = _uiState.value.copy(
                        errorMessageResId = R.string.error_user_not_logged_in
                    )
                    return@launch
                }

                _uiState.value = _uiState.value.copy(
                    isSavingName = true
                )

                firestore
                    .collection(COLLECTION_USERS)
                    .document(currentUser.uid)
                    .update(FIELD_NAME, trimmedName)
                    .await()

                _uiState.value = _uiState.value.copy(
                    name = trimmedName,
                    isSavingName = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSavingName = false,
                    errorMessageResId = R.string.error_updating_name
                )
            }
        }
    }

    fun updateAvatar(avatarId: Int) {
        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser ?: return@launch

                firestore
                    .collection(COLLECTION_USERS)
                    .document(currentUser.uid)
                    .update("avatarId", avatarId)
                    .await()

                _uiState.value = _uiState.value.copy(
                    avatarId = avatarId
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessageResId = R.string.error_loading_profile
                )
            }
        }
    }
}