package com.example.f1fantasyleague.ui.admin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.data.repository.AdminResultsRepository
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import com.example.f1fantasyleague.ui.theme.SurfaceSecondary
import kotlinx.coroutines.launch

@Composable
fun AdminResultsScreen() {
    var selectedRound by rememberSaveable { mutableStateOf("1") }
    var raceName by rememberSaveable { mutableStateOf("") }
    var raceDate by rememberSaveable { mutableStateOf("") }

    var qualifyingResults by rememberSaveable {
        mutableStateOf(List(10) { "" })
    }

    var raceResults by rememberSaveable {
        mutableStateOf(List(10) { "" })
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { AdminResultsRepository() }

    val resultsSavedMessage = stringResource(R.string.msg_results_saved)
    val resultsSaveErrorMessage = stringResource(R.string.msg_results_save_error)
    val raceSavedMessage = stringResource(R.string.msg_race_saved)
    val raceSaveErrorMessage = stringResource(R.string.msg_race_save_error)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = SurfaceSecondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.admin_race_info),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = selectedRound,
                    onValueChange = { selectedRound = it },
                    label = { Text(stringResource(R.string.round_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = raceName,
                    onValueChange = { raceName = it },
                    label = { Text(stringResource(R.string.admin_race_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = raceDate,
                    onValueChange = { raceDate = it },
                    label = { Text(stringResource(R.string.admin_race_date)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                repository.saveRace(
                                    round = selectedRound,
                                    raceName = raceName,
                                    raceDate = raceDate
                                )

                                Toast.makeText(
                                    context,
                                    raceSavedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    raceSaveErrorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.admin_save_race))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = SurfaceSecondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.admin_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = selectedRound,
                    onValueChange = { selectedRound = it },
                    label = { Text(stringResource(R.string.round_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        onValueChange = { value ->
                            qualifyingResults = qualifyingResults.toMutableList().also {
                                it[i] = value.uppercase()
                            }
                        },
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
                        onValueChange = { value ->
                            raceResults = raceResults.toMutableList().also {
                                it[i] = value.uppercase()
                            }
                        },
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
                                    qualifyingTop10 = qualifyingResults,
                                    raceTop10 = raceResults
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
    }
}