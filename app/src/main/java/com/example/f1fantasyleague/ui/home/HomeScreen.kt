package com.example.f1fantasyleague.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.f1fantasyleague.R

private val cardShape = RoundedCornerShape(30.dp)
private val outerPadding = 20.dp
private val sectionPadding = 24.dp
private val tableCellStartPadding = 12.dp
private val tableCellVerticalPadding = 10.dp
private val borderWidth = 1.dp
private val tableDividerVerticalPadding = 2.dp
private val padding14 = 14.dp

@Composable
fun HomeScreen() {
    var passcode by remember { mutableStateOf("") }
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()


    val standingsRows = listOf(
        listOf("1", "Pav", "0", "65"),
        listOf("2", "Dud", "0", "39"),
        listOf("3", "Sro", "0", "36"),
        listOf("4", "Bur", "0", "33")
    )

    val guessesRows = listOf(
        listOf("Luka P.", "RUS/ANT/PIA", "ANT/RUS/PIA", "-"),
        listOf("Matej D.", "RUS/ANT/PIA", "ANT/RUS/PIA", "-"),
        listOf("Marin S.", "RUS/ANT/PIA", "ANT/RUS/PIA", "25"),
        listOf("Bruno B", "RUS/ANT/PIA", "ANT/RUS/PIA", "-")
    )

    val predictionViewModel: PredictionViewModel = viewModel()
    val predictionUiState by predictionViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(predictionUiState.messageResId) {
        predictionUiState.messageResId?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            predictionViewModel.clearMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = outerPadding, bottom = outerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = outerPadding)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = SurfacePrimary),
                border = BorderStroke(borderWidth, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = padding14)
            ) {
                SectionCard(title = stringResource(R.string.next_race))
                Column(
                    modifier = Modifier.padding(
                        horizontal = sectionPadding,
                        vertical = sectionPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(tableCellStartPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.number_of_races),
                        modifier = Modifier.fillMaxWidth(),
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(R.string.time_of_race),
                        modifier = Modifier.fillMaxWidth(),
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text =  stringResource(R.string.time_to_race),
                        modifier = Modifier.fillMaxWidth(),
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = outerPadding)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = SurfacePrimary),
                border = BorderStroke(borderWidth, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = padding14)
            ) {
                SectionCard(title = stringResource(R.string.guess_title))
                Column(
                    modifier = Modifier.padding(
                        horizontal = sectionPadding,
                        vertical = sectionPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(tableCellStartPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = predictionUiState.qualifyingGuess,
                        onValueChange = predictionViewModel::onQualifyingGuessChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.qualifying_guess)) },
                        placeholder = { Text(stringResource(R.string.guess_placeholder)) },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = predictionUiState.raceGuess,
                        onValueChange = predictionViewModel::onRaceGuessChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.race_guess)) },
                        placeholder = { Text(stringResource(R.string.guess_placeholder)) },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = predictionUiState.mysteryGuess,
                        onValueChange = predictionViewModel::onMysteryGuessChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.mystery_guess)) },
                        placeholder = { Text("VER") },
                        singleLine = true
                    )

                    Button(
                        onClick = predictionViewModel::submitPrediction,
                        enabled = !predictionUiState.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                    ) {
                        Text(
                            text = stringResource(R.string.submit_guess),
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            TableCard(
                title = stringResource(R.string.mystery_guess_title),
                header = listOf(stringResource(R.string.R), stringResource(R.string.guess)),
                rows = homeUiState.mysteryQuestions
            )

            TableCard(
                title = stringResource(R.string.standings_title),
                header = listOf(
                    stringResource(R.string.table_rank_header),
                    stringResource(R.string.table_name_header),
                    stringResource(R.string.table_wins_header),
                    stringResource(R.string.table_points_header)
                ),
                rows = standingsRows
            )

            TableCard(
                title = stringResource(R.string.guesses_title),
                header = listOf(
                    stringResource(R.string.table_name_header),
                    stringResource(R.string.table_qualifying_header),
                    stringResource(R.string.table_race_header),
                    stringResource(R.string.table_mystery_header)
                ),
                rows = homeUiState.guessesRows
            )
        }
    }
}

@Composable
fun TableCard(
    title: String,
    header: List<String>,
    rows: List<List<String>>
) {
    Card(
        modifier = Modifier
            .padding(horizontal = outerPadding)
            .fillMaxWidth(),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = SurfacePrimary),
        border = BorderStroke(borderWidth, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = padding14)
    ) {
        SectionCard(title = title)

        Column(
            modifier = Modifier.padding(horizontal = sectionPadding, vertical = sectionPadding),
            verticalArrangement = Arrangement.spacedBy(tableCellStartPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        .background(SurfaceSecondary)
                        .border(BorderStroke(borderWidth, BorderSubtle))
                ) {
                    header.forEachIndexed { index, column ->
                        Text(
                            text = column,
                            modifier = Modifier
                                .width(160.dp)
                                .padding(
                                    start = tableCellStartPadding,
                                    top = tableCellVerticalPadding,
                                    bottom = tableCellVerticalPadding
                                ),
                            color = TextPrimary,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        if (index < header.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = tableDividerVerticalPadding)
                                    .width(borderWidth)
                                    .background(BorderSubtle)
                            )
                        }
                    }
                }

                rows.forEach { row ->
                    Row(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .height(IntrinsicSize.Min)
                            .background(SurfaceSecondary)
                            .border(BorderStroke(borderWidth, BorderSubtle))
                    ) {
                        row.forEachIndexed { index, cell ->
                            Text(
                                text = cell,
                                modifier = Modifier
                                    .width(160.dp)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = tableCellVerticalPadding,
                                        bottom = tableCellVerticalPadding
                                    ),
                                color = TextSecondary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                            if (index < row.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = tableDividerVerticalPadding)
                                        .width(borderWidth)
                                        .background(BorderSubtle)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = outerPadding, topEnd = outerPadding))
            .background(BrandPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(horizontal = sectionPadding, vertical = tableCellVerticalPadding),
            color = TextPrimary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}