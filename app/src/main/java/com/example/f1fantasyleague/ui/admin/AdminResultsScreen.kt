package com.example.f1fantasyleague.ui.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import com.example.f1fantasyleague.data.repository.AdminResultsRepository
import kotlinx.coroutines.launch

@Composable
fun AdminResultsScreen() {
    var selectedRound by remember { mutableStateOf("1") }

    val qualifyingResults = remember {
        mutableStateListOf("", "", "", "", "", "", "", "", "", "")
    }

    val raceResults = remember {
        mutableStateListOf("", "", "", "", "", "", "", "", "", "")
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { AdminResultsRepository() }

    val resultsSavedMessage = stringResource(R.string.msg_results_saved)
    val resultsSaveErrorMessage = stringResource(R.string.msg_results_save_error)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.admin_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = selectedRound,
            onValueChange = { selectedRound = it },
            label = { Text(stringResource(R.string.round_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.admin_qualifying_top_10),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 0 until 10) {
            OutlinedTextField(
                value = qualifyingResults[i],
                onValueChange = { qualifyingResults[i] = it.uppercase() },
                label = { Text("Q${i + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.admin_race_top_10),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 0 until 10) {
            OutlinedTextField(
                value = raceResults[i],
                onValueChange = { raceResults[i] = it.uppercase() },
                label = { Text("R${i + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        repository.saveRaceWeekendResults(
                            round = selectedRound,
                            qualifyingTop10 = qualifyingResults.toList(),
                            raceTop10 = raceResults.toList()
                        )

                        Toast.makeText(
                            context,
                            resultsSavedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            resultsSaveErrorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.admin_save_results))
        }
    }
}