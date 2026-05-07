package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.scoring.PointsCalculator
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ScoresRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun calculateAndSaveScores(
        raceWeekendId: String,
        actualQualifyingTop10: List<String>,
        actualRaceTop10: List<String>
    ) {
        val predictionsSnapshot = firestore
            .collection("predictions")
            .document("round$raceWeekendId")
            .collection("users")
            .get()
            .await()

        predictionsSnapshot.documents.forEach { document ->
            val userId = document.id

            val predictedQualifyingTop3 =
                document.get("qualifyingTop3") as? List<String> ?: emptyList()

            val predictedRaceTop3 =
                document.get("raceTop3") as? List<String> ?: emptyList()

            val mysteryGuessPoints =
                document.getLong("mysteryGuessPoints")?.toInt() ?: 0

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
                "raceWeekendId" to raceWeekendId,
                "qualifyingPoints" to qualifyingPoints,
                "racePoints" to racePoints,
                "mysteryPoints" to mysteryGuessPoints,
                "totalPoints" to totalPoints
            )

            firestore
                .collection("users")
                .document(userId)
                .collection("scores")
                .document("round$raceWeekendId")
                .set(score)
                .await()
        }
    }
}