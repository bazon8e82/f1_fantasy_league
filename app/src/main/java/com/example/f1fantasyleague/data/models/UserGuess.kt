package com.example.f1fantasyleague.data.models

data class UserGuess(
    val userId: String,
    val name: String,
    val qualifyingTop3: List<String>,
    val raceTop3: List<String>,
    val mysteryGuess: String
)
