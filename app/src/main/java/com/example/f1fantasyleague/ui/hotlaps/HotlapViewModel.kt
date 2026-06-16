package com.example.f1fantasyleague.ui.hotlaps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.repository.HotlapRepository
import com.example.f1fantasyleague.data.repository.RaceTrackHotlaps
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HotlapUiState(
    val raceTrackHotlaps: List<RaceTrackHotlaps> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HotlapViewModel(
    private val hotlapRepository: HotlapRepository = HotlapRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HotlapUiState())
    val uiState: StateFlow<HotlapUiState> = _uiState

    init {
        loadHotlaps()
    }

    fun loadHotlaps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val hotlaps = hotlapRepository.getHotlaps()
                _uiState.update {
                    it.copy(raceTrackHotlaps = hotlaps, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }
}