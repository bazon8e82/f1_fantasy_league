package com.example.f1fantasyleague.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.ui.theme.*

private val cardShape = RoundedCornerShape(30.dp)
private val outerPadding = 20.dp
private val sectionPadding = 24.dp
private val borderWidth = 1.dp
private val padding14 = 14.dp

@Composable
fun InfoScreenContent() {
    val topSections = listOf(
        stringResource(R.string.how_to_vote_title) to
                stringResource(R.string.how_to_vote_desc),

        stringResource(R.string.when_title) to
                stringResource(R.string.when_desc)
    )

    val bottomSections = listOf(
        stringResource(R.string.qualifying_title) to
                stringResource(R.string.qualifying_desc),

        stringResource(R.string.race_title) to
                stringResource(R.string.race_desc),

        stringResource(R.string.mystery_title) to
                stringResource(R.string.mystery_desc),

        stringResource(R.string.max_points_title) to
                stringResource(R.string.max_points_desc),

        stringResource(R.string.points_distribution_title) to
                stringResource(R.string.points_distribution_desc),

        stringResource(R.string.season_points_title) to
                stringResource(R.string.season_points_desc),

        stringResource(R.string.gp_wins_title) to
                stringResource(R.string.gp_wins_desc)
    )

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
                .padding(
                    top = outerPadding,
                    bottom = outerPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = SurfacePrimary),
                border = BorderStroke(borderWidth, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = padding14)
            ) {
                SectionCard(title = stringResource(R.string.rules_title))

                Column(
                    modifier = Modifier.padding(
                        horizontal = sectionPadding,
                        vertical = sectionPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    topSections.forEach { section ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = section.first,
                                color = TextPrimary,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = section.second,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Text(
                        text = stringResource(R.string.scoring_title),
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    bottomSections.forEach { section ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = section.first,
                                color = TextPrimary,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = section.second,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}