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

        predictionDocument.data?.keys?.forEach { userId ->
            updateUserTotalPoints(userId)
        }

        updateUserWins()

        updateUserTitles()
    }

    private suspend fun updateUserTotalPoints(userId: String) {
        val scoresSnapshot = firestore
            .collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_SCORES)
            .get()
            .await()

        val totalPoints = scoresSnapshot.documents.sumOf { document ->
            document.getLong(FIELD_TOTAL_POINTS)?.toInt() ?: 0
        }

        firestore
            .collection(COLLECTION_USERS)
            .document(userId)
            .update(FIELD_POINTS, totalPoints)
            .await()
    }

    private suspend fun updateUserWins() {
        val usersSnapshot = firestore
            .collection(COLLECTION_USERS)
            .get()
            .await()

        val userWins = usersSnapshot.documents.associate { document ->
            document.id to 0
        }.toMutableMap()

        val allRoundIds = usersSnapshot.documents
            .flatMap { document ->
                firestore
                    .collection(COLLECTION_USERS)
                    .document(document.id)
                    .collection(COLLECTION_SCORES)
                    .get()
                    .await()
                    .documents
                    .map { it.id }
            }
            .distinct()

        allRoundIds.forEach { roundId ->
            val roundScores = usersSnapshot.documents.mapNotNull { userDocument ->
                val scoreDocument = firestore
                    .collection(COLLECTION_USERS)
                    .document(userDocument.id)
                    .collection(COLLECTION_SCORES)
                    .document(roundId)
                    .get()
                    .await()

                val totalPoints = scoreDocument.getLong(FIELD_TOTAL_POINTS)?.toInt()
                    ?: return@mapNotNull null

                userDocument.id to totalPoints

            }

            val maxPoints = roundScores.maxOfOrNull { it.second } ?: return@forEach

            roundScores
                .filter { it.second == maxPoints }
                .forEach { winner ->
                    userWins[winner.first] = (userWins[winner.first] ?: 0) + 1
                }
        }

        usersSnapshot.documents.forEach { userDocument ->
            firestore
                .collection(COLLECTION_USERS)
                .document(userDocument.id)
                .update(FIELD_WINS, userWins[userDocument.id] ?: 0)
                .await()
        }
    }

    private suspend fun updateUserTitles() {
        val usersSnapshot = firestore
            .collection(COLLECTION_USERS)
            .get()
            .await()

        val usersWithPoints = usersSnapshot.documents.map { document ->
            document.id to (document.getLong(FIELD_POINTS)?.toInt() ?: 0)
        }

        val maxPoints = usersWithPoints.maxOfOrNull { it.second } ?: return

        usersWithPoints.forEach { user ->
            val titles = if (user.second == maxPoints && maxPoints > 0) {
                1
            } else {
                0
            }

            firestore
                .collection(COLLECTION_USERS)
                .document(user.first)
                .update(FIELD_TITLES, titles)
                .await()
        }
    }


}