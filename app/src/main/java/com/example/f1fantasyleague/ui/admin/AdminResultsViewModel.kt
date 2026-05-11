package com.example.f1fantasyleague.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.repository.AdminResultsRepository
import com.example.f1fantasyleague.data.repository.MysteryQuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminResultsUiState(
    val selectedRound: String = "1",
    val raceName: String = "",
    val raceDate: String = "",
    val qualifyingResults: List<String> = List(10) { "" },
    val raceResults: List<String> = List(10) { "" },
    val mysteryQuestion: String = "",
    val mysteryAnswer: String = "",
    val message: AdminResultsMessage? = null
)

enum class AdminResultsMessage {
    RACE_SAVED,
    RACE_SAVE_ERROR,
    RESULTS_SAVED,
    RESULTS_SAVE_ERROR,
    MYSTERY_QUESTION_SAVED,
    MYSTERY_QUESTION_SAVE_ERROR
}

class AdminResultsViewModel(
    private val adminResultsRepository: AdminResultsRepository = AdminResultsRepository(),
    private val mysteryQuestionRepository: MysteryQuestionRepository = MysteryQuestionRepository()
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

    fun onRaceDateChange(value: String) {
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
}