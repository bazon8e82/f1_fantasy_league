package com.example.f1fantasyleague.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_POINTS
import com.example.f1fantasyleague.data.firestore.FIELD_TITLES
import com.example.f1fantasyleague.data.firestore.FIELD_WINS
import com.example.f1fantasyleague.data.models.ResultUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ResultsRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getResults(): List<ResultUser> {
        val snapshot = firestore
            .collection(COLLECTION_USERS)
            .get()
            .await()

        return snapshot.documents.mapIndexed { index, document ->
            ResultUser(
                position = index + 1,
                name = document.getString(FIELD_NAME) ?: "",
                shortName = getShortName(document.getString(FIELD_NAME) ?: ""),
                titles = document.getLong(FIELD_TITLES)?.toInt() ?: 0,
                wins = document.getLong(FIELD_WINS)?.toInt() ?: 0,
                points = document.getLong(FIELD_POINTS)?.toInt() ?: 0,
                best = "-",
                seasons = getCurrentSeason()
            )
        }.sortedByDescending { it.points }
            .mapIndexed { index, user ->
                user.copy(position = index + 1)
            }
    }

    private fun getShortName(name: String): String {
        return name
            .split(" ")
            .mapNotNull { it.firstOrNull()?.uppercase() }
            .joinToString("")
    }

    private fun getCurrentSeason(): String {
        val year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) % 100
        val nextYear = (year + 1) % 100

        return "'$year-'$nextYear"
    }
}