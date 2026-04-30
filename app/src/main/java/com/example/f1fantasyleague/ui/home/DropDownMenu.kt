package com.example.f1fantasyleague.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.f1fantasyleague.ui.theme.TextPrimary
import com.example.f1fantasyleague.ui.theme.TextSelected
import androidx.compose.ui.res.stringResource
import com.example.f1fantasyleague.R

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
            title = stringResource(R.string.menu_home),
            isSelected = currentScreen == "home",
            onClick = { onItemClick("home") }
        )

        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = stringResource(R.string.menu_info),
            isSelected = currentScreen == "info",
            onClick = { onItemClick("info") }
        )

        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = stringResource(R.string.menu_standings),
            isSelected = currentScreen == "standings",
            onClick = { onItemClick("standings") }
        )

        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = stringResource(R.string.menu_results),
            isSelected = currentScreen == "results",
            onClick = { onItemClick("results") }
        )

        Spacer(modifier = Modifier.height(26.dp))

        MenuItem(
            title = stringResource(R.string.menu_hotlaps),
            isSelected = currentScreen == "hotlaps",
            onClick = { onItemClick("hotlaps") }
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(R.string.menu_logout),
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