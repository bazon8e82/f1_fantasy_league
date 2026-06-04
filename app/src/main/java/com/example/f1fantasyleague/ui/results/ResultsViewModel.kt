package com.example.f1fantasyleague.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1fantasyleague.data.models.ResultUser
import com.example.f1fantasyleague.data.repository.ResultsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ResultsUiState(
    val users: List<ResultUser> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ResultsViewModel(
    private val resultsRepository: ResultsRepository = ResultsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    init {
        loadResults()
    }

    private fun loadResults() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val users = resultsRepository.getResults()

                _uiState.value = ResultsUiState(
                    users = users,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = ResultsUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}