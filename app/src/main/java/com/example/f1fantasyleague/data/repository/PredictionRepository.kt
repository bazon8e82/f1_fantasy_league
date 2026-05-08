package com.example.f1fantasyleague.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PredictionRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun savePrediction(
        round: String,
        qualifyingTop3Input: String,
        raceTop3Input: String,
        mysteryGuess: String
    ) {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")

        val qualifyingTop3 = parseDriverCodes(qualifyingTop3Input)
        val raceTop3 = parseDriverCodes(raceTop3Input)

        val data = hashMapOf(
            "email" to user.email,
            "qualifyingTop3" to qualifyingTop3,
            "raceTop3" to raceTop3,
            "mysteryGuess" to mysteryGuess.uppercase().trim()
        )

        firestore
            .collection("predictions")
            .document("round$round")
            .set(
                mapOf(
                    user.uid to data
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )
            .await()
    }

    private fun parseDriverCodes(input: String): List<String> {
        return input
            .split("/")
            .map { it.trim().uppercase() }
            .filter { it.isNotBlank() }
    }
}