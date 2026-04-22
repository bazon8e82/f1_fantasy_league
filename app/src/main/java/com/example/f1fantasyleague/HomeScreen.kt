package com.example.f1fantasyleague

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.F1Theme

@Composable
fun HomeScreen() {
    val cardShape = RoundedCornerShape(30.dp)
    var passcode by remember { mutableStateOf("") }
    val tableCellStartPadding = 12.dp
    val tableDividerWidth = 0.8.dp
    val standingsColNumberWidth = 56.dp
    val standingsColNameWidth = 120.dp
    val standingsColWinsWidth = 88.dp
    val standingsColPointsWidth = 100.dp
    val standingsRows = listOf(
        listOf("1", "Pav", "0", "65"),
        listOf("2", "Dud", "0", "39"),
        listOf("3", "Sro", "0", "36"),
        listOf("4", "Bur", "0", "33")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(F1Theme.colors.backgroundPrimary),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = F1Theme.colors.surfacePrimary),
                border = BorderStroke(0.8.dp, F1Theme.colors.borderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(F1Theme.colors.brandPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Miami Grand Prix",
                            modifier = Modifier.padding(horizontal = 24.dp),
                            color = F1Theme.colors.textPrimary,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Race 4/22",
                            color = F1Theme.colors.textPrimary,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "02/05, 22:00",
                            color = F1Theme.colors.textSecondary,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "11d 10h 32m 20s",
                            color = F1Theme.colors.textSecondary,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = F1Theme.colors.surfacePrimary),
                border = BorderStroke(0.8.dp, F1Theme.colors.borderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(F1Theme.colors.brandPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Make a guess",
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = F1Theme.colors.textPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Passcode",
                        color = F1Theme.colors.textSecondary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = passcode,
                        onValueChange = { passcode = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = F1Theme.colors.brandPrimary)
                    ) {
                        Text(
                            text = "Check",
                            color = F1Theme.colors.textPrimary,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = F1Theme.colors.surfacePrimary),
                border = BorderStroke(0.8.dp, F1Theme.colors.borderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(F1Theme.colors.brandPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mystery Guess Table",
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = F1Theme.colors.textPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mystery Guesses",
                        color = F1Theme.colors.textPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .background(F1Theme.colors.surfaceSecondary)
                                .border(BorderStroke(0.8.dp, F1Theme.colors.borderSubtle))
                        ) {
                            Text(
                                text = "R",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 2.dp)
                                    .width(tableDividerWidth)
                                    .background(F1Theme.colors.borderSubtle)
                            )
                            Text(
                                text = "Guess",
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        for (round in 1..3) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                                    .background(F1Theme.colors.surfaceSecondary)
                                    .border(BorderStroke(0.8.dp, F1Theme.colors.borderSubtle))
                            ) {
                                Text(
                                    text = round.toString(),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 2.dp)
                                        .width(tableDividerWidth)
                                        .background(F1Theme.colors.borderSubtle)
                                )
                                Text(
                                    text = "#",
                                    modifier = Modifier
                                        .weight(2f)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = F1Theme.colors.surfacePrimary),
                border = BorderStroke(0.8.dp, F1Theme.colors.borderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(F1Theme.colors.brandPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Standings",
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = F1Theme.colors.textPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
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
                                .background(F1Theme.colors.surfaceSecondary)
                                .border(BorderStroke(0.8.dp, F1Theme.colors.borderSubtle))
                        ) {
                            Text(
                                text = "#",
                                modifier = Modifier
                                    .width(standingsColNumberWidth)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 2.dp)
                                    .width(tableDividerWidth)
                                    .background(F1Theme.colors.borderSubtle)
                            )
                            Text(
                                text = "Name",
                                modifier = Modifier
                                    .width(standingsColNameWidth)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 2.dp)
                                    .width(tableDividerWidth)
                                    .background(F1Theme.colors.borderSubtle)
                            )
                            Text(
                                text = "Wins",
                                modifier = Modifier
                                    .width(standingsColWinsWidth)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 2.dp)
                                    .width(tableDividerWidth)
                                    .background(F1Theme.colors.borderSubtle)
                            )
                            Text(
                                text = "Points",
                                modifier = Modifier
                                    .width(standingsColPointsWidth)
                                    .padding(
                                        start = tableCellStartPadding,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                color = F1Theme.colors.textPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        for (row in standingsRows) {
                            Row(
                                modifier = Modifier
                                    .width(IntrinsicSize.Min)
                                    .height(IntrinsicSize.Min)
                                    .background(F1Theme.colors.surfaceSecondary)
                                    .border(BorderStroke(0.8.dp, F1Theme.colors.borderSubtle))
                            ) {
                                Text(
                                    text = row[0],
                                    modifier = Modifier
                                        .width(standingsColNumberWidth)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 2.dp)
                                        .width(tableDividerWidth)
                                        .background(F1Theme.colors.borderSubtle)
                                )
                                Text(
                                    text = row[1],
                                    modifier = Modifier
                                        .width(standingsColNameWidth)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 2.dp)
                                        .width(tableDividerWidth)
                                        .background(F1Theme.colors.borderSubtle)
                                )
                                Text(
                                    text = row[2],
                                    modifier = Modifier
                                        .width(standingsColWinsWidth)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 2.dp)
                                        .width(tableDividerWidth)
                                        .background(F1Theme.colors.borderSubtle)
                                )
                                Text(
                                    text = row[3],
                                    modifier = Modifier
                                        .width(standingsColPointsWidth)
                                        .padding(
                                            start = tableCellStartPadding,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = F1Theme.colors.textSecondary,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF161616)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
