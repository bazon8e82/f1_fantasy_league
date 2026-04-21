package com.example.f1fantasyleague

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropDownMenu(
    currentScreen: String,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MenuItem(
            title = "Home",
            isSelected = currentScreen == "home",
            onClick = { onItemClick("home") }
        )
        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = "Info",
            isSelected = currentScreen == "info",
            onClick = { onItemClick("info") }
        )
        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = "Standings",
            isSelected = currentScreen == "standings",
            onClick = { onItemClick("standings") }
        )
        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = "Results",
            isSelected = currentScreen == "results",
            onClick = { onItemClick("results") }
        )
        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = "Hotlaps",
            isSelected = currentScreen == "hotlaps",
            onClick = { onItemClick("hotlaps") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = "Lock",
            tint = Color(0xFF8A8A95)
        )
    }
}

@Composable
fun MenuItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = title,
        color = if (isSelected) Color(0xFFF2F2F2) else Color(0xFF8A8A95),
        fontSize = 24.sp,
        modifier = Modifier.clickable { onClick() }
    )
}