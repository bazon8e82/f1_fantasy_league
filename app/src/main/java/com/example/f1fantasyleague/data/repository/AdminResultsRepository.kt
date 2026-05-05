package com.example.f1fantasyleague.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminResultsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveRaceWeekendResults(
        round: String,
        qualifyingTop10: List<String>,
        raceTop10: List<String>
    ) {
        val data = hashMapOf(
            "round" to round,
            "qualifyingTop10" to qualifyingTop10,
            "raceTop10" to raceTop10,
            "savedAt" to Timestamp.now()
        )

        firestore
            .collection("raceResults")
            .document("round_$round")
            .set(data)
            .await()
    }
}