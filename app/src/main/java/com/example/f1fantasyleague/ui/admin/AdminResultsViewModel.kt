package com.example.f1fantasyleague.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.repository.AdminResultsRepository
import com.example.f1fantasyleague.data.repository.HotlapRepository
import com.example.f1fantasyleague.data.repository.MysteryQuestionRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class User(
    val id: String = "", val name: String = ""
)

data class AdminResultsUiState(
    val selectedRound: String = "1",
    val raceName: String = "",
    val raceDate: Timestamp? = null,
    val qualifyingResults: List<String> = List(10) { "" },
    val raceResults: List<String> = List(10) { "" },
    val mysteryQuestion: String = "",
    val mysteryAnswer: String = "",
    val raceTrack: String = "",
    val lapTime: String = "",
    val message: AdminResultsMessage? = null,
    val users: List<User> = emptyList(),
    val selectedUser: User? = null,
    val isHost: Boolean = false,
    val isHostExpanded: Boolean = false,
    val isUserExpanded: Boolean = false
)

enum class AdminResultsMessage {
    RACE_SAVED, RACE_SAVE_ERROR, RESULTS_SAVED, RESULTS_SAVE_ERROR, MYSTERY_QUESTION_SAVED, MYSTERY_QUESTION_SAVE_ERROR, HOTLAP_SAVED, HOTLAP_SAVE_ERROR, LOAD_USERS_ERROR, HOTLAP_NO_TRACK, HOTLAP_NO_USER, HOTLAP_NO_TIME
}

class AdminResultsViewModel(
    private val adminResultsRepository: AdminResultsRepository = AdminResultsRepository,
    private val mysteryQuestionRepository: MysteryQuestionRepository = MysteryQuestionRepository,
    private val hotlapRepository: HotlapRepository = HotlapRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminResultsUiState())
    val uiState: StateFlow<AdminResultsUiState> = _uiState

    fun onRoundChange(value: String) {
        _uiState.update {
            it.copy(selectedRound = value)
        }
    }

    fun onRaceNameChange(value: String) {
        _uiState.update {
            it.copy(raceName = value)
        }
    }

    fun onRaceDateChange(value: Timestamp) {
        _uiState.update {
            it.copy(raceDate = value)
        }
    }

    fun onQualifyingResultChange(index: Int, value: String) {
        _uiState.update { state ->
            val updatedResults = state.qualifyingResults.toMutableList()
            updatedResults[index] = value.uppercase()

            state.copy(qualifyingResults = updatedResults)
        }
    }

    fun onRaceResultChange(index: Int, value: String) {
        _uiState.update { state ->
            val updatedResults = state.raceResults.toMutableList()
            updatedResults[index] = value.uppercase()

            state.copy(raceResults = updatedResults)
        }
    }

    fun onMysteryQuestionChange(value: String) {
        _uiState.update {
            it.copy(mysteryQuestion = value)
        }
    }

    fun onMysteryAnswerChange(value: String) {
        _uiState.update {
            it.copy(mysteryAnswer = value.uppercase())
        }
    }

    fun saveRace() {
        viewModelScope.launch {
            val state = _uiState.value

            try {
                adminResultsRepository.saveRace(
                    round = state.selectedRound,
                    raceName = state.raceName,
                    raceDate = state.raceDate
                )

                _uiState.update {
                    it.copy(message = AdminResultsMessage.RACE_SAVED)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.RACE_SAVE_ERROR)
                }
            }
        }
    }

    fun saveResults() {
        viewModelScope.launch {
            val state = _uiState.value

            try {
                adminResultsRepository.saveRaceWeekendResults(
                    round = state.selectedRound,
                    qualifyingTop10 = state.qualifyingResults,
                    raceTop10 = state.raceResults
                )

                _uiState.update {
                    it.copy(message = AdminResultsMessage.RESULTS_SAVED)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.RESULTS_SAVE_ERROR)
                }
            }
        }
    }

    fun saveMysteryQuestion() {
        viewModelScope.launch {
            val state = _uiState.value

            try {
                mysteryQuestionRepository.saveMysteryQuestion(
                    round = state.selectedRound,
                    question = state.mysteryQuestion,
                    answer = state.mysteryAnswer
                )

                _uiState.update {
                    it.copy(message = AdminResultsMessage.MYSTERY_QUESTION_SAVED)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.MYSTERY_QUESTION_SAVE_ERROR)
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update {
            it.copy(message = null)
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val snapshot = firestore.collection(COLLECTION_USERS).get().await()
                val users = snapshot.documents.map { doc ->
                    User(
                        id = doc.id, name = doc.getString(FIELD_NAME) ?: ""
                    )
                }
                _uiState.update {
                    it.copy(users = users)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.LOAD_USERS_ERROR)
                }
            }
        }
    }

    fun onUserSelected(user: User) {
        _uiState.update {
            it.copy(selectedUser = user, isUserExpanded = false)
        }
    }

    fun onHostSelected(isHost: Boolean) {
        _uiState.update {
            it.copy(isHost = isHost, isHostExpanded = false)
        }
    }

    fun onUserExpandedChange(expanded: Boolean) {
        _uiState.update {
            it.copy(isUserExpanded = expanded)
        }
    }

    fun onHostExpandedChange(expanded: Boolean) {
        _uiState.update {
            it.copy(isHostExpanded = expanded)
        }
    }

    fun onRaceTrackChange(value: String) {
        _uiState.update {
            it.copy(raceTrack = value)
        }
    }

    fun onLapTimeChange(value: String) {
        _uiState.update {
            it.copy(lapTime = value)
        }
    }

    fun saveHotlap() {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.raceTrack.isBlank()) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.HOTLAP_NO_TRACK)
                }
                return@launch
            }

            if (state.selectedUser == null) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.HOTLAP_NO_USER)
                }
                return@launch
            }

            if (state.lapTime.isBlank()) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.HOTLAP_NO_TIME)
                }
                return@launch
            }

            try {
                hotlapRepository.saveHotlap(
                    raceTrack = state.raceTrack,
                    selectedUser = state.selectedUser.name,
                    isHost = state.isHost,
                    lapTime = state.lapTime,
                )

                _uiState.update {
                    it.copy(message = AdminResultsMessage.HOTLAP_SAVED)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(message = AdminResultsMessage.HOTLAP_SAVE_ERROR)
                }
            }
        }
    }
}