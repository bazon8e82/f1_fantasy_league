package com.example.f1fantasyleague

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun InfoScreenContent() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.rules_title),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            items(topSections) { section ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = section.first,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = section.second,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.scoring_title),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            items(bottomSections) { section ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = section.first,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = section.second,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = stringResource(R.string.scroll_top_desc),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}