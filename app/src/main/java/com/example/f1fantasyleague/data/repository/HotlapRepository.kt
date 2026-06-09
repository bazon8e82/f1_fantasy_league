package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.firestore.COLLECTION_HOTLAPS
import com.example.f1fantasyleague.data.firestore.IS_HOST
import com.example.f1fantasyleague.data.firestore.LAP_TIME
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Hotlap(
    val user: String = "",
    val isHost: Boolean = false,
    val lapTime: String = ""
)

data class RaceTrackHotlaps(
    val raceTrack: String = "",
    val hotlaps: List<Hotlap> = emptyList()
)

object HotlapRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveHotlap(
        raceTrack: String,
        selectedUser: String,
        isHost: Boolean,
        lapTime: String,
    ) {
        val data = hashMapOf(
            IS_HOST to isHost,
            LAP_TIME to lapTime
        )

        firestore
            .collection(COLLECTION_HOTLAPS)
            .document(raceTrack)
            .set(
                mapOf(
                    selectedUser to data
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )
            .await()
    }

    suspend fun getHotlaps(): List<RaceTrackHotlaps> {
        val snapshot = firestore
            .collection(COLLECTION_HOTLAPS)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            val raceTrack = doc.id
            val hotlaps = doc.data?.mapNotNull { (userName, value) ->
                if (value is Map<*, *>) {
                    Hotlap(
                        user = userName,
                        isHost = value[IS_HOST] as? Boolean ?: false,
                        lapTime = value[LAP_TIME] as? String ?: ""
                    )
                } else null
            } ?: emptyList()

            RaceTrackHotlaps(
                raceTrack = raceTrack,
                hotlaps = hotlaps
            )
        }
    }
}