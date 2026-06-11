package com.example.f1fantasyleague.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1fantasyleague.R
import com.example.f1fantasyleague.ui.theme.BorderSubtle
import com.example.f1fantasyleague.ui.theme.BrandPrimary
import com.example.f1fantasyleague.ui.theme.SurfaceSecondary
import com.example.f1fantasyleague.ui.theme.TextPrimary
import com.example.f1fantasyleague.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isEditNameSheetVisible by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf("") }
    var isAvatarSheetVisible by remember { mutableStateOf(false) }

    when {
        uiState.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = TextPrimary)
            }
        }

        uiState.errorMessageResId != null -> {
            Text(
                text = stringResource(uiState.errorMessageResId!!),
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        else -> {
            val userName = uiState.name.ifBlank {
                stringResource(R.string.profile_unknown_user)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ProfileHeader(
                    name = userName,
                    email = uiState.email,
                    avatarId = uiState.avatarId,
                    onEditNameClick = {
                        editedName = uiState.name
                        isEditNameSheetVisible = true
                    },
                    onAvatarClick = {
                        isAvatarSheetVisible = true
                    }
                )

                ProfileStatCard(
                    title = stringResource(R.string.profile_total_points),
                    value = uiState.points.toString()
                )

                ProfileStatCard(
                    title = stringResource(R.string.profile_weekend_wins),
                    value = uiState.wins.toString()
                )

                ProfileStatCard(
                    title = stringResource(R.string.profile_best_weekend),
                    value = "${uiState.bestWeekendScore} pts"
                )
            }
        }
    }

    if (isEditNameSheetVisible) {
        EditNameBottomSheet(
            name = editedName,
            isSaving = uiState.isSavingName,
            onNameChange = { editedName = it },
            onDismiss = {
                isEditNameSheetVisible = false
            },
            onSave = {
                viewModel.updateName(editedName)
                isEditNameSheetVisible = false
            }
        )
    }

    if (isAvatarSheetVisible) {
        AvatarBottomSheet(
            userName = uiState.name.ifBlank {
                stringResource(R.string.profile_unknown_user)
            },
            selectedAvatarId = uiState.avatarId,
            onAvatarSelected = { avatarId ->
                viewModel.updateAvatar(avatarId)
                isAvatarSheetVisible = false
            },
            onDismiss = {
                isAvatarSheetVisible = false
            }
        )
    }
}

@Composable
private fun ProfileHeader(
    name: String,
    email: String,
    avatarId: Int,
    onEditNameClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .size(76.dp)
                .background(
                    color = getAvatarColor(avatarId),
                    shape = CircleShape
                )
                .clickable { onAvatarClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = getInitials(name),
                color = TextPrimary,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    color = TextPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_name),
                    tint = TextSecondary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onEditNameClick() }
                )
            }

            Text(
                text = email,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditNameBottomSheet(
    name: String,
    isSaving: Boolean,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = SurfaceSecondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.edit_name),
                color = TextPrimary,
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = onSave,
                enabled = !isSaving && name.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    color = TextPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AvatarBottomSheet(
    userName: String,
    selectedAvatarId: Int,
    onAvatarSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = SurfaceSecondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.choose_avatar),
                color = TextPrimary,
                style = MaterialTheme.typography.headlineSmall
            )

            AvatarOption(0, selectedAvatarId, userName, "Default", onAvatarSelected)
            AvatarOption(1, selectedAvatarId, userName, "Ferrari", onAvatarSelected)
            AvatarOption(2, selectedAvatarId, userName, "McLaren", onAvatarSelected)
            AvatarOption(3, selectedAvatarId, userName, "Red Bull", onAvatarSelected)
            AvatarOption(4, selectedAvatarId, userName, "Aston Martin", onAvatarSelected)
            AvatarOption(5, selectedAvatarId, userName, "Mercedes", onAvatarSelected)
        }
    }
}

@Composable
private fun AvatarOption(
    avatarId: Int,
    selectedAvatarId: Int,
    userName: String,
    title: String,
    onAvatarSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAvatarSelected(avatarId) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = getAvatarColor(avatarId),
                    shape = CircleShape
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = getInitials(userName),
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = if (avatarId == selectedAvatarId) "$title ✓" else title,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ProfileStatCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderSubtle,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceSecondary
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = value,
                color = TextPrimary,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

private fun getInitials(name: String): String {
    return name
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
}

private fun getAvatarColor(avatarId: Int): Color {
    return when (avatarId) {
        1 -> BrandPrimary
        2 -> Color(0xFFFF8700)
        3 -> Color(0xFF1E41FF)
        4 -> Color(0xFF006F62)
        5 -> Color(0xFF00A19C)
        else -> BrandPrimary
    }
}