package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.models.ResultUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ResultsRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getResults(): List<ResultUser> {
        val snapshot = firestore
            .collection("users")
            .get()
            .await()

        return snapshot.documents.mapIndexed { index, document ->
            ResultUser(
                position = index + 1,
                name = document.getString("name") ?: "",
                shortName = getShortName(document.getString("name") ?: ""),
                titles = 0,
                wins = document.getLong("wins")?.toInt() ?: 0,
                points = document.getLong("points")?.toInt() ?: 0,
                best = "-",
                seasons = "'25-'26"
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
}