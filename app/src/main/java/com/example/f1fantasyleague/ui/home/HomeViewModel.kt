package com.example.f1fantasyleague.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.repository.MysteryQuestionRepository
import com.example.f1fantasyleague.data.RaceRepository
import com.example.f1fantasyleague.data.models.Race
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val mysteryQuestions: List<List<String>> = emptyList(),
    val isLoading: Boolean = false
    val currentRace: Race? = null,
    val nextRaceNumber: String = "",
    val raceDate: String = "",
    val countdownText: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val mysteryQuestionRepository = MysteryQuestionRepository()
class HomeViewModel(
    private val raceRepository: RaceRepository = RaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var countdownJob: Job? = null

    fun loadMysteryQuestions() {
    init {
        loadInitialRace()
    }

    private fun loadInitialRace() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.update { it.copy(isLoading = true, error = null) }

            val raceResult = raceRepository.getRace()
            raceResult.onSuccess { race ->
                updateRaceState(race)
                startCountdown()
            }

            val questions = mysteryQuestionRepository.getAllMysteryQuestions()
            raceResult.onFailure { error ->
                _uiState.update {
                    it.copy(
                        currentRace = null,
                        nextRaceNumber = "",
                        raceDate = "",
                        countdownText = "",
                        isLoading = false,
                        error = error.message ?: NO_UPCOMING_RACES_MESSAGE
                    )
                }
            }
        }
    }

    private fun loadNextRace(currentRaceDate: com.google.firebase.Timestamp?) {
        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }
            val nextRaceResult = raceRepository.getRace(currentRaceDate)
            nextRaceResult.onSuccess { race ->
                updateRaceState(race)
                startCountdown()
            }

            _uiState.value = _uiState.value.copy(
                mysteryQuestions = questions,
                isLoading = false
            )
        }
    }
}
            nextRaceResult.onFailure { error ->
                _uiState.update {
                    it.copy(
                        currentRace = null,
                        nextRaceNumber = "",
                        raceDate = "",
                        countdownText = "",
                        isLoading = false,
                        error = error.message ?: NO_UPCOMING_RACES_MESSAGE
                    )
                }
            }
        }
    }

    private fun updateRaceState(race: Race) {
        val raceTimestamp = race.raceDate?.toDate()?.time
        val initialCountdown = if (raceTimestamp != null) {
            formatCountdown((raceTimestamp - System.currentTimeMillis()).coerceAtLeast(0L))
        } else {
            ""
        }

        _uiState.update { state ->
            state.copy(
                currentRace = race,
                nextRaceNumber = "Race ${race.raceId}/22",
                raceDate = formatDate(race.raceDate?.toDate()),
                countdownText = initialCountdown,
                isLoading = false,
                error = null
            )
        }
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (true) {
                val state = _uiState.value
                val currentRace = state.currentRace ?: return@launch
                val raceDate = currentRace.raceDate?.toDate() ?: return@launch
                val currentTime = System.currentTimeMillis()
                val raceTime = raceDate.time

                if (currentTime >= raceTime) {
                    loadNextRace(currentRace.raceDate)
                    return@launch
                }

                val timeRemaining = raceTime - currentTime
                val countdown = formatCountdown(timeRemaining)

                _uiState.update { it.copy(countdownText = countdown) }

                delay(1000)
            }
        }
    }

    private fun formatCountdown(timeInMillis: Long): String {
        val days = TimeUnit.MILLISECONDS.toDays(timeInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60

        return when {
            days > 0 -> String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
            else -> String.format("%02d:%02d", minutes, seconds)
        }
    }

    private fun formatDate(date: java.util.Date?): String {
        if (date == null) return ""
        val dateFormat =
            java.text.SimpleDateFormat("dd.MM.yyyy HH:mm", java.util.Locale.getDefault())
        return dateFormat.format(date)
    }

    companion object {
        private const val NO_UPCOMING_RACES_MESSAGE = "No upcoming races found"
    }
}
