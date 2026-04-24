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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.PrimaryRed
import com.example.f1fantasyleague.ui.theme.TextMuted
import com.example.f1fantasyleague.ui.theme.TextPrimary
import com.example.f1fantasyleague.ui.theme.TextSelected

@Composable
fun DropDownMenu(
    currentScreen: String,
    onItemClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
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

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Logout",
            color = TextPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.clickable {
                onLogoutClick()
            }
        )
/*
        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = "Lock",
            tint = TextMuted
        )
*/
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
        color = if (isSelected) TextSelected else TextPrimary,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.clickable { onClick() }
    )
}