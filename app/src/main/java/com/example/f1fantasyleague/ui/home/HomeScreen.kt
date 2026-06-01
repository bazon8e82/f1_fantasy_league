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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.ui.theme.BackgroundPrimary
import com.example.f1fantasyleague.ui.theme.BorderSubtle
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import com.example.f1fantasyleague.ui.theme.SurfacePrimary
import com.example.f1fantasyleague.ui.theme.SurfaceSecondary
import com.example.f1fantasyleague.ui.theme.TextPrimary
import com.example.f1fantasyleague.ui.theme.TextSecondary

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
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val mysteryGuesses = uiState.mysteryQuestions

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
                SectionCard(
                    title = uiState.currentRace?.raceName
                        ?: stringResource(R.string.next_race)
                )
                Column(
                    modifier = Modifier.padding(
                        horizontal = sectionPadding,
                        vertical = sectionPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(tableCellStartPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = BrandPrimary)
                    } else {
                        if (uiState.nextRaceNumber.isNotBlank()) {
                            Text(
                                text = uiState.nextRaceNumber,
                                modifier = Modifier.fillMaxWidth(),
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }

                        if (uiState.raceDate.isNotBlank()) {
                            Text(
                                text = uiState.raceDate,
                                modifier = Modifier.fillMaxWidth(),
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }

                        if (uiState.countdownText.isNotBlank()) {
                            Text(
                                text = uiState.countdownText,
                                modifier = Modifier.fillMaxWidth(),
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (uiState.error != null) {
                            Text(
                                text = uiState.error ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
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
                rows = mysteryGuesses
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
                rows = guessesRows
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
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val headerStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
    val bodyStyle = MaterialTheme.typography.headlineSmall
    val columnWidths = header.indices.map { columnIndex ->
        val headerWidth = textMeasurer
            .measure(text = AnnotatedString(header[columnIndex]), style = headerStyle)
            .size
            .width

        val rowsWidth = rows.maxOfOrNull { row ->
            textMeasurer
                .measure(
                    text = AnnotatedString(row.getOrNull(columnIndex).orEmpty()),
                    style = bodyStyle
                )
                .size
                .width
        } ?: 0

        with(density) { maxOf(headerWidth, rowsWidth).toDp() } + tableCellStartPadding * 2
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
                                .width(columnWidths.getOrElse(index) { 0.dp })
                                .padding(
                                    horizontal = tableCellStartPadding,
                                    vertical = tableCellVerticalPadding
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
                                    .width(columnWidths.getOrElse(index) { 0.dp })
                                    .padding(
                                        horizontal = tableCellStartPadding,
                                        vertical = tableCellVerticalPadding
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
