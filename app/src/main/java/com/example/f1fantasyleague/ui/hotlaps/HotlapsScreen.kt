package com.example.f1fantasyleague.ui.hotlaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1fantasyleague.ui.home.TableCard

private val outerPadding = 20.dp

@Composable
fun HotlapsScreen(
    viewModel: HotlapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = outerPadding, bottom = outerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                uiState.raceTrackHotlaps.forEach { raceTrackHotlaps ->
                    val sortedHotlaps = raceTrackHotlaps.hotlaps.sortedByDescending { it.isHost }
                    val leaderTime = sortedHotlaps.firstOrNull()?.lapTime ?: ""

                    val rows = sortedHotlaps.mapIndexed { index, hotlap ->
                        val gap = if (index == 0) "Leader" else calculateGap(leaderTime, hotlap.lapTime)
                        val rank = if (hotlap.isHost) "Host" else "${index}"
                        listOf(rank, hotlap.user, hotlap.lapTime, gap)
                    }

                    TableCard(
                        title = raceTrackHotlaps.raceTrack,
                        header = listOf("#", "Name", "Time", "Gap"),
                        rows = rows
                    )
                }
            }
        }
    }
}

private fun calculateGap(leaderTime: String, currentTime: String): String {
    return try {
        val leaderParts = leaderTime.split(":", ".")
        val currentParts = currentTime.split(":", ".")

        if (leaderParts.size == 3 && currentParts.size == 3) {
            val leaderMs = leaderParts[0].toInt() * 60000 + leaderParts[1].toInt() * 1000 + leaderParts[2].toInt()
            val currentMs = currentParts[0].toInt() * 60000 + currentParts[1].toInt() * 1000 + currentParts[2].toInt()
            val diff = currentMs - leaderMs

            val seconds = diff / 1000
            val millis = diff % 1000
            "+${seconds}.${millis.toString().padStart(3, '0')}"
        } else {
            "+0.000"
        }
    } catch (e: Exception) {
        "+0.000"
    }
}