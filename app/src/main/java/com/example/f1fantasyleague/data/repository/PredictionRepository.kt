package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.firestore.COLLECTION_PREDICTIONS
import com.example.f1fantasyleague.data.firestore.DOCUMENT_ROUND_PREFIX
import com.example.f1fantasyleague.data.firestore.FIELD_EMAIL
import com.example.f1fantasyleague.data.firestore.FIELD_MYSTERY_GUESS
import com.example.f1fantasyleague.data.firestore.FIELD_QUALIFYING_TOP_3
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_TOP_3
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

        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$round"

        val data = hashMapOf(
            FIELD_EMAIL to user.email,
            FIELD_QUALIFYING_TOP_3 to qualifyingTop3,
            FIELD_RACE_TOP_3 to raceTop3,
            FIELD_MYSTERY_GUESS to mysteryGuess.uppercase().trim()
        )

        firestore
            .collection(COLLECTION_PREDICTIONS)
            .document(roundDocumentId)
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