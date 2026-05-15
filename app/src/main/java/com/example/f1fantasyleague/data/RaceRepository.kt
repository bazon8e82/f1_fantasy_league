package com.example.f1fantasyleague.data

import android.annotation.SuppressLint
import com.example.f1fantasyleague.data.models.Race
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val COLLECTION_PATH_RACES = "races"
private const val FIELD_RACE_DATE = "raceDate"
private const val NO_UPCOMING_RACES = "No upcoming races found"
private const val FAILED_PARSING = "Failed to parse race data"

object RaceRepository {
    val db: FirebaseFirestore get() = FirebaseFirestore.getInstance()

    suspend fun getRace(raceDate: Timestamp? = Timestamp.now()): Result<Race> {
        return try {
            val documents = db.collection(COLLECTION_PATH_RACES)
                .orderBy(FIELD_RACE_DATE)
                .startAt(raceDate)
                .limit(1)
                .get()
                .await()

            if (documents.isEmpty) {
                return Result.failure(Exception(NO_UPCOMING_RACES))
            }

            val race = documents.documents.first().toObject(Race::class.java)
                ?: return Result.failure(Exception(FAILED_PARSING))
            Result.success(race)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
