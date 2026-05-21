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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import com.example.f1fantasyleague.ui.theme.SurfaceSecondary

@Composable
fun AdminResultsScreen(
    viewModel: AdminResultsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val resultsSavedMessage = stringResource(R.string.msg_results_saved)
    val resultsSaveErrorMessage = stringResource(R.string.msg_results_save_error)
    val raceSavedMessage = stringResource(R.string.msg_race_saved)
    val raceSaveErrorMessage = stringResource(R.string.msg_race_save_error)
    val mysteryQuestionSavedMessage = stringResource(R.string.msg_mystery_question_saved)
    val mysteryQuestionSaveErrorMessage = stringResource(R.string.msg_mystery_question_save_error)

    LaunchedEffect(uiState.message) {
        when (uiState.message) {
            AdminResultsMessage.RACE_SAVED -> {
                Toast.makeText(context, raceSavedMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.RACE_SAVE_ERROR -> {
                Toast.makeText(context, raceSaveErrorMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.RESULTS_SAVED -> {
                Toast.makeText(context, resultsSavedMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.RESULTS_SAVE_ERROR -> {
                Toast.makeText(context, resultsSaveErrorMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.MYSTERY_QUESTION_SAVED -> {
                Toast.makeText(context, mysteryQuestionSavedMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.MYSTERY_QUESTION_SAVE_ERROR -> {
                Toast.makeText(context, mysteryQuestionSaveErrorMessage, Toast.LENGTH_SHORT).show()
            }

            null -> Unit
        }

        if (uiState.message != null) {
            viewModel.clearMessage()
        }
    }

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
                    value = uiState.selectedRound,
                    onValueChange = viewModel::onRoundChange,
                    label = { Text(stringResource(R.string.round_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.raceName,
                    onValueChange = viewModel::onRaceNameChange,
                    label = { Text(stringResource(R.string.admin_race_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.raceDate,
                    onValueChange = viewModel::onRaceDateChange,
                    label = { Text(stringResource(R.string.admin_race_date)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = viewModel::saveRace,
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
                    value = uiState.selectedRound,
                    onValueChange = viewModel::onRoundChange,
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
                        value = uiState.qualifyingResults[i],
                        onValueChange = { value ->
                            viewModel.onQualifyingResultChange(i, value)
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
                        value = uiState.raceResults[i],
                        onValueChange = { value ->
                            viewModel.onRaceResultChange(i, value)
                        },
                        label = { Text("R${i + 1}") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = viewModel::saveResults,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.admin_save_results))
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
                    text = stringResource(R.string.admin_mystery_question),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.selectedRound,
                    onValueChange = viewModel::onRoundChange,
                    label = { Text(stringResource(R.string.round_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.mysteryQuestion,
                    onValueChange = viewModel::onMysteryQuestionChange,
                    label = { Text(stringResource(R.string.admin_mystery_question)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.mysteryAnswer,
                    onValueChange = viewModel::onMysteryAnswerChange,
                    label = { Text(stringResource(R.string.admin_correct_answer)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = viewModel::saveMysteryQuestion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.admin_save_mystery_question))
                }
            }
        }
    }
}