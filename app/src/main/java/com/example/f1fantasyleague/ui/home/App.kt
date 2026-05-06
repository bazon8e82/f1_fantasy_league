package com.example.f1fantasyleague.ui.home

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
import com.example.f1fantasyleague.ui.theme.*
import com.example.f1fantasyleague.ui.admin.AdminResultsScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun App(
    onLogout: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("home") }
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
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
                currentUserEmail = currentUserEmail,
                onItemClick = { screen ->
                    currentScreen = screen
                    menuExpanded = false
                },
                onLogoutClick = {
                    menuExpanded = false
                    onLogout()
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPrimary)
                .padding(if (currentScreen == "home") 0.dp else 24.dp)
        ) {
            when (currentScreen) {
                "home" -> HomeScreen()
                "info" -> InfoScreenContent()
                "standings" -> ScreenPlaceholder("Standings")
                "results" -> ScreenPlaceholder("Results")
                "hotlaps" -> ScreenPlaceholder("Hotlaps")
                "admin" -> AdminResultsScreen()
            }
        }
    }
}

@Composable
fun ScreenPlaceholder(title: String) {
    Text(
        text = title,
        color = TextPrimary,
        style = MaterialTheme.typography.titleLarge
    )
}