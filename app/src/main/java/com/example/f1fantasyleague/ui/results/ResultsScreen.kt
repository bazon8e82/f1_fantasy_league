package com.example.f1fantasyleague.ui.results

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.f1fantasyleague.data.models.ResultUser
import com.example.f1fantasyleague.ui.theme.BackgroundPrimary
import com.example.f1fantasyleague.ui.theme.BorderSubtle
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import com.example.f1fantasyleague.ui.theme.SurfacePrimary
import com.example.f1fantasyleague.ui.theme.TextPrimary
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


private val cardShape = RoundedCornerShape(30.dp)
private val cardPadding = 24.dp
private val sectionSpacing = 24.dp
private val borderWidth = 1.dp

@Composable
fun ResultsScreen(
    viewModel: ResultsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val users = uiState.users

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
            .padding(vertical = cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.results_title_name),
            color = TextPrimary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(sectionSpacing))

        users.forEach { user ->
            ResultCard(user = user)

            Spacer(modifier = Modifier.height(sectionSpacing))
        }
    }
}

@Composable
private fun ResultCard(user: ResultUser) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = SurfacePrimary
        ),
        border = BorderStroke(borderWidth, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandPrimary)
                    .padding(vertical = 18.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${user.position}. ${user.name} (${user.shortName})",
                    color = TextPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.padding(cardPadding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ResultInfo(
                        title = stringResource(R.string.results_title),
                        value = user.titles.toString()
                    )

                    ResultInfo(
                        title = stringResource(R.string.results_wins),
                        value = user.wins.toString()
                    )

                    ResultInfo(
                        title = stringResource(R.string.results_points),
                        value = user.points.toString()
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ResultInfo(
                        title = stringResource(R.string.results_best),
                        value = user.best
                    )

                    ResultInfo(
                        title = stringResource(R.string.results_seasons),
                        value = user.seasons
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultInfo(
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = value,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}