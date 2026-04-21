package com.example.f1fantasyleague

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    var menuExpanded by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("home") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(
            onMenuClick = {
                menuExpanded = !menuExpanded
            }
        )

        AnimatedVisibility(
            visible = menuExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            DropDownMenu(
                currentScreen = currentScreen,
                onItemClick = { selectedScreen ->
                    currentScreen = selectedScreen
                    menuExpanded = false
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            when (currentScreen) {
                "home" -> ScreenPlaceholder("Home screen")
                "info" -> InfoScreenContent()
                "standings" -> ScreenPlaceholder("Standings")
                "results" -> ScreenPlaceholder("Results")
                "hotlaps" -> ScreenPlaceholder("Hotlaps")
            }
        }
    }
}

@Composable
fun ScreenPlaceholder(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge
    )
}