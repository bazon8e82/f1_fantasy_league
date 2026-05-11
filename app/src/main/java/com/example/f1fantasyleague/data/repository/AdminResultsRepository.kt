package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.firestore.COLLECTION_RACES
import com.example.f1fantasyleague.data.firestore.COLLECTION_RACE_RESULTS
import com.example.f1fantasyleague.data.firestore.DOCUMENT_ROUND_PREFIX
import com.example.f1fantasyleague.data.firestore.FIELD_QUALIFYING_TOP_10
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_DATE
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_ID
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_RACE_TOP_10
import com.example.f1fantasyleague.data.firestore.FIELD_ROUND
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminResultsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveRaceWeekendResults(
        round: String,
        qualifyingTop10: List<String>,
        raceTop10: List<String>
    ) {
        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$round"

        val data = hashMapOf(
            FIELD_ROUND to round,
            FIELD_QUALIFYING_TOP_10 to qualifyingTop10,
            FIELD_RACE_TOP_10 to raceTop10,
        )

        firestore
            .collection(COLLECTION_RACE_RESULTS)
            .document(roundDocumentId)
            .set(data)
            .await()

        ScoresRepository.calculateAndSaveScores(
            raceWeekendId = round,
            actualQualifyingTop10 = qualifyingTop10,
            actualRaceTop10 = raceTop10
        )
    }

    suspend fun saveRace(
        round: String,
        raceName: String,
        raceDate: String
    ) {
        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$round"

        val data = hashMapOf(
            FIELD_RACE_ID to round,
            FIELD_RACE_NAME to raceName,
            FIELD_RACE_DATE to raceDate
        )

        firestore
            .collection(COLLECTION_RACES)
            .document(roundDocumentId)
            .set(data)
            .await()
    }
}