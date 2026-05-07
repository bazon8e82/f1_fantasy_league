package com.example.f1fantasyleague.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.repository.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.f1fantasyleague.R

data class PredictionUiState(
    val round: String = "4",
    val qualifyingGuess: String = "",
    val raceGuess: String = "",
    val mysteryGuess: String = "",
    val isLoading: Boolean = false,
    val messageResId: Int? = null
)

class PredictionViewModel(
    private val repository: PredictionRepository = PredictionRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PredictionUiState())
    val uiState: StateFlow<PredictionUiState> = _uiState.asStateFlow()

    fun onQualifyingGuessChange(value: String) {
        _uiState.update {
            it.copy(qualifyingGuess = formatInput(value))
        }
    }

    fun onRaceGuessChange(value: String) {
        _uiState.update {
            it.copy(raceGuess = formatInput(value))
        }
    }

    fun onMysteryGuessChange(value: String) {
        _uiState.update {
            it.copy(mysteryGuess = value.trim().uppercase())
        }
    }

    fun submitPrediction() {
        val state = _uiState.value

        val qualifyingTop3 = parseTop3(state.qualifyingGuess)
        val raceTop3 = parseTop3(state.raceGuess)
        val mysteryGuess = state.mysteryGuess.trim().uppercase()

        if (qualifyingTop3.size != 3 || raceTop3.size != 3 || mysteryGuess.isBlank()) {
            _uiState.update {
                it.copy(messageResId = R.string.msg_prediction_invalid)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, messageResId = null) }

            try {
                repository.savePrediction(
                    round = state.round,
                    qualifyingTop3Input = state.qualifyingGuess,
                    raceTop3Input = state.raceGuess,
                    mysteryGuess = mysteryGuess
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messageResId = R.string.msg_prediction_saved
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messageResId = R.string.msg_prediction_error
                    )
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update {
            it.copy(messageResId = null)
        }
    }

    private fun parseTop3(input: String): List<String> {
        return input
            .split("/")
            .map { it.trim().uppercase() }
            .filter { it.isNotBlank() }
    }

    private fun formatInput(value: String): String {
        return value.uppercase()
    }
}