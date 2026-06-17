package com.example.f1fantasyleague.data

import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_POINTS
import com.example.f1fantasyleague.data.firestore.FIELD_WINS
import com.example.f1fantasyleague.data.models.StandingUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class StandingsRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun observeStandings(): Flow<List<StandingUser>> = callbackFlow {
        val registration = db.collection(COLLECTION_USERS)
            .orderBy(FIELD_POINTS, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val standings = snapshot?.documents?.mapNotNull { document ->
                    val name = document.getString(FIELD_NAME)?.trim().orEmpty()
                    if (name.isBlank()) {
                        return@mapNotNull null
                    }

                    StandingUser(
                        name = name,
                        points = (document.getLong(FIELD_POINTS) ?: 0L).toInt(),
                        wins = (document.getLong(FIELD_WINS) ?: 0L).toInt()
                    )
                }.orEmpty()

                trySend(standings)
            }

        awaitClose {
            registration.remove()
        }
    }
}
