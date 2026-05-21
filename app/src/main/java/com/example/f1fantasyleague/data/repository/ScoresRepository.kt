package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.scoring.PointsCalculator
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.f1fantasyleague.data.firestore.*

object ScoresRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun calculateAndSaveScores(
        raceWeekendId: String,
        actualQualifyingTop10: List<String>,
        actualRaceTop10: List<String>
    ) {
        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$raceWeekendId"

        val predictionDocument = firestore
            .collection(COLLECTION_PREDICTIONS)
            .document(roundDocumentId)
            .get()
            .await()

        val batch = firestore.batch()

        predictionDocument.data?.forEach { entry ->
            val userId = entry.key
            val prediction = entry.value as? Map<*, *> ?: return@forEach

            val predictedQualifyingTop3 =
                prediction[FIELD_QUALIFYING_TOP_3] as? List<String> ?: emptyList()

            val predictedRaceTop3 =
                prediction[FIELD_RACE_TOP_3] as? List<String> ?: emptyList()

            val mysteryGuessPoints =
                (prediction[FIELD_MYSTERY_GUESS_POINTS] as? Long)?.toInt() ?: 0

            val qualifyingPoints = PointsCalculator.calculateQualifyingPoints(
                predictedTop3 = predictedQualifyingTop3,
                actualTop10 = actualQualifyingTop10
            )

            val racePoints = PointsCalculator.calculateRacePoints(
                predictedTop3 = predictedRaceTop3,
                actualTop10 = actualRaceTop10
            )

            val totalPoints = qualifyingPoints + racePoints + mysteryGuessPoints

            val score = mapOf(
                FIELD_RACE_WEEKEND_ID to raceWeekendId,
                FIELD_QUALIFYING_POINTS to qualifyingPoints,
                FIELD_RACE_POINTS to racePoints,
                FIELD_MYSTERY_POINTS to mysteryGuessPoints,
                FIELD_TOTAL_POINTS to totalPoints
            )

            val scoreDocumentReference = firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_SCORES)
                .document(roundDocumentId)

            batch.set(scoreDocumentReference, score)
        }

        batch.commit().await()
    }
}