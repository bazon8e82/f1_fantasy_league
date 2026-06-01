package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.firestore.COLLECTION_PREDICTIONS
import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.DOCUMENT_ROUND_PREFIX
import com.example.f1fantasyleague.data.firestore.FIELD_EMAIL
import com.example.f1fantasyleague.data.firestore.FIELD_MYSTERY_GUESS
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_QUALIFYING_TOP_3
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_TOP_3
import com.example.f1fantasyleague.data.firestore.FIELD_ROUND
import com.example.f1fantasyleague.data.models.UserGuess
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PredictionRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun savePrediction(
        roundId: Int,
        qualifyingTop3Input: String,
        raceTop3Input: String,
        mysteryGuess: String
    ) {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        require(roundId > 0) { "Invalid round id" }

        val qualifyingTop3 = parseDriverCodes(qualifyingTop3Input)
        val raceTop3 = parseDriverCodes(raceTop3Input)
        val normalizedMysteryGuess = mysteryGuess.uppercase().trim()

        require(qualifyingTop3.size == 3) { "Qualifying top 3 must contain 3 drivers" }
        require(raceTop3.size == 3) { "Race top 3 must contain 3 drivers" }
        require(normalizedMysteryGuess.isNotBlank()) { "Mystery guess is required" }

        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$roundId"

        val data = hashMapOf(
            FIELD_EMAIL to user.email,
            FIELD_QUALIFYING_TOP_3 to qualifyingTop3,
            FIELD_RACE_TOP_3 to raceTop3,
            FIELD_MYSTERY_GUESS to normalizedMysteryGuess,
            FIELD_ROUND to roundId
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

    suspend fun getPredictionsForRound(roundId: Int): List<UserGuess> {
        require(roundId > 0) { "Invalid round id" }
        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$roundId"
        val snapshot = firestore
            .collection(COLLECTION_PREDICTIONS)
            .document(roundDocumentId)
            .get()
            .await()

        val predictions = snapshot.data ?: return emptyList()
        val predictionEntries = predictions.mapNotNull { entry ->
            val userId = entry.key as? String ?: return@mapNotNull null
            val prediction = entry.value as? Map<*, *> ?: return@mapNotNull null
            val predictionRoundId = (prediction[FIELD_ROUND] as? Number)?.toInt()
            if (predictionRoundId != null && predictionRoundId != roundId) {
                return@mapNotNull null
            }
            userId to prediction
        }

        if (predictionEntries.isEmpty()) {
            return emptyList()
        }

        val userIds = predictionEntries.map { (userId, _) -> userId }
        val nameByUserId = mutableMapOf<String, String>()

        userIds.chunked(10).forEach { chunk ->
            val usersSnapshot = firestore
                .collection(COLLECTION_USERS)
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()

            usersSnapshot.documents.forEach { document ->
                val name = document.getString(FIELD_NAME)
                    ?: document.getString(FIELD_EMAIL)
                    ?: ""
                nameByUserId[document.id] = name
            }
        }

        return predictionEntries.map { (userId, prediction) ->
            val name = nameByUserId[userId]
                .takeUnless { it.isNullOrBlank() }
                ?: (prediction[FIELD_EMAIL] as? String)?.trim().orEmpty()

            val qualifyingTop3 = (prediction[FIELD_QUALIFYING_TOP_3] as? List<*>)
                ?.filterIsInstance<String>()
                ?: emptyList()

            val raceTop3 = (prediction[FIELD_RACE_TOP_3] as? List<*>)
                ?.filterIsInstance<String>()
                ?: emptyList()

            val mysteryGuess = (prediction[FIELD_MYSTERY_GUESS] as? String).orEmpty()

            UserGuess(
                userId = userId,
                name = name.ifBlank { userId },
                qualifyingTop3 = qualifyingTop3,
                raceTop3 = raceTop3,
                mysteryGuess = mysteryGuess
            )
        }
    }

    private fun parseDriverCodes(input: String): List<String> {
        return input
            .split("/")
            .map { it.trim().uppercase() }
            .filter { it.isNotBlank() }
    }
}