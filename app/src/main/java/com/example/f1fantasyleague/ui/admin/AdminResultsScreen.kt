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
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.f1fantasyleague.ui.theme.SurfaceSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminResultsScreen(
    viewModel: AdminResultsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    val resultsSavedMessage = stringResource(R.string.msg_results_saved)
    val resultsSaveErrorMessage = stringResource(R.string.msg_results_save_error)
    val raceSavedMessage = stringResource(R.string.msg_race_saved)
    val raceSaveErrorMessage = stringResource(R.string.msg_race_save_error)
    val mysteryQuestionSavedMessage = stringResource(R.string.msg_mystery_question_saved)
    val mysteryQuestionSaveErrorMessage = stringResource(R.string.msg_mystery_question_save_error)
    val hotlapSavedMessage = stringResource(R.string.msg_hotlap_saved)
    val hotlapSaveErrorMessage = stringResource(R.string.msg_hotlap_save_error)
    val hotlapNoTrackMessage = stringResource(R.string.msg_hotlap_no_track)
    val hotlapNoUserMessage = stringResource(R.string.msg_hotlap_no_user)
    val hotlapNoTimeMessage = stringResource(R.string.msg_hotlap_no_time)
    val loadUsersErrorMessage = stringResource(R.string.msg_load_users_error)

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

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

            AdminResultsMessage.HOTLAP_SAVED -> {
                Toast.makeText(context, hotlapSavedMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.HOTLAP_SAVE_ERROR -> {
                Toast.makeText(context, hotlapSaveErrorMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.HOTLAP_NO_TRACK -> {
                Toast.makeText(context, hotlapNoTrackMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.HOTLAP_NO_USER -> {
                Toast.makeText(context, hotlapNoUserMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.HOTLAP_NO_TIME -> {
                Toast.makeText(context, hotlapNoTimeMessage, Toast.LENGTH_SHORT).show()
            }

            AdminResultsMessage.LOAD_USERS_ERROR -> {
                Toast.makeText(context, loadUsersErrorMessage, Toast.LENGTH_SHORT).show()
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

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    val displayDate = uiState.raceDate?.let {
                        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(it.toDate())
                    } ?: ""
                    OutlinedTextField(
                        value = displayDate,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.admin_race_date)) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledBorderColor = MaterialTheme.colorScheme.outline,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

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

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = SurfaceSecondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.admin_hotlaps),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.raceTrack,
                    onValueChange = viewModel::onRaceTrackChange,
                    label = { Text(stringResource(R.string.admin_race_track)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = uiState.isUserExpanded,
                    onExpandedChange = { viewModel.onUserExpandedChange(it) }
                ) {
                    OutlinedTextField(
                        value = uiState.selectedUser?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select User") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isUserExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = uiState.isUserExpanded,
                        onDismissRequest = { viewModel.onUserExpandedChange(false) }
                    ) {
                        uiState.users.forEach { user ->
                            DropdownMenuItem(
                                text = { Text(user.name) },
                                onClick = { viewModel.onUserSelected(user) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = uiState.isHostExpanded,
                    onExpandedChange = { viewModel.onHostExpandedChange(it) }
                ) {
                    OutlinedTextField(
                        value = if (uiState.isHost) "Yes" else "No",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Host") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isHostExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = uiState.isHostExpanded,
                        onDismissRequest = { viewModel.onHostExpandedChange(false) }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Yes") },
                            onClick = { viewModel.onHostSelected(true) }
                        )
                        DropdownMenuItem(
                            text = { Text("No") },
                            onClick = { viewModel.onHostSelected(false) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.lapTime,
                    onValueChange = viewModel::onLapTimeChange,
                    label = { Text(stringResource(R.string.admin_hotlap_time)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = viewModel::saveHotlap,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.admin_save_hotlap))
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        showDatePicker = false
                        showTimePicker = true
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        showTimePicker = false
                        val selectedDateMillis = datePickerState.selectedDateMillis
                        if (selectedDateMillis != null) {
                            val date = Date(selectedDateMillis)
                            val calendar = java.util.Calendar.getInstance().apply {
                                time = date
                                set(java.util.Calendar.HOUR_OF_DAY, timePickerState.hour)
                                set(java.util.Calendar.MINUTE, timePickerState.minute)
                            }
                            viewModel.onRaceDateChange(Timestamp(calendar.time))
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { showTimePicker = false }
                ) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}