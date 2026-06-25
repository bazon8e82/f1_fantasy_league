package com.example.f1fantasyleague.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.RaceRepository
import com.example.f1fantasyleague.data.models.Race
import com.example.f1fantasyleague.data.repository.MysteryQuestionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

data class HomeUiState(
    val mysteryQuestions: List<List<String>> = emptyList(),
    val currentRace: Race? = null,
    val nextRaceNumber: String = "",
    val raceDate: String = "",
    val countdownText: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val notificationsScheduledRaceId: Int? = null
)

class HomeViewModel(
    private val raceRepository: RaceRepository = RaceRepository,
    private val mysteryQuestionRepository: MysteryQuestionRepository = MysteryQuestionRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val raceResult = raceRepository.getRace()
            raceResult.onSuccess { race ->
                updateRaceState(race)
                startCountdown()
            }

            raceResult.onFailure { error ->
                _uiState.update {
                    it.copy(
                        currentRace = null,
                        nextRaceNumber = "",
                        raceDate = "",
                        countdownText = "",
                        error = error.message ?: NO_UPCOMING_RACES_MESSAGE
                    )
                }
            }

            val questions = mysteryQuestionRepository.getAllMysteryQuestions()

            _uiState.update {
                it.copy(
                    mysteryQuestions = questions,
                    isLoading = false
                )
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
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun markNotificationsScheduled(raceId: Int) {
        _uiState.update {
            it.copy(notificationsScheduledRaceId = raceId)
        }
    }

    companion object {
        private const val NO_UPCOMING_RACES_MESSAGE = "No upcoming races found"
    }
}
