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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun InfoScreenContent() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val topSections = listOf(
        "How to vote?" to
                "Submit your top 3 Qualifying and Race drivers in order that you think they will finish. For fun you can also do a Mystery Guess.",

        "When?" to
                "The voting is open before the Qualifying of the weekend that Race starts.",
    )

    val bottomSections = listOf(
        "Qualifying Top 3" to
                "Up to 6 Race Points per Driver available based on how many places you were off with your guess.",

        "Race Top 3" to
                "Up to 8 Race Points per Driver available based on how many places you were off with your guess.",

        "Mystery Guess" to
                "A different guess each race worth up to 8 Race Points",

        "Maximum Race Points per Race Weekend" to
                "18 + 24 + 8 (Q + R + MG) = 50 Race Points",

        "Points Distribution based on Race Points" to
                "25, 18, 15, 12, 10, 8, 6, 4, 2, 1",

        "Maximum Season Points" to
                "25 (First Place) * 22 (Races) = 550 Points",

        "Grand Prix Wins" to
                "How many individual Race Weekends a player had the most points in."
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A2A2A))
            .padding(24.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5A623)) // žuta
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RULES",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(topSections) { section ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = section.first,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = section.second,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
                    )
                }
            }

            item {
                Text(
                    text = "Scoring system",
                    color = Color(0xFFF5A623),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
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
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = section.second,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
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
            containerColor = Color.Red,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Scroll to top",
                tint = Color.White
            )
        }
    }
}