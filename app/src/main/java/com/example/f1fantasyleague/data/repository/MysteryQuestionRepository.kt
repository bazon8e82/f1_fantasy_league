package com.example.f1fantasyleague.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MysteryQuestionRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveMysteryQuestion(
        round: String,
        question: String,
        answer: String
    ) {
        val data = hashMapOf(
            "round" to round,
            "question" to question,
            "answer" to answer.uppercase(),
        )

        firestore
            .collection("mysteryQuestions")
            .document("round$round")
            .set(data)
            .await()
    }

    suspend fun getAllMysteryQuestions(): List<List<String>> {
        val snapshot = firestore
            .collection("mysteryQuestions")
            .get()
            .await()

        return snapshot.documents.map { document ->
            listOf(
                document.getString("round") ?: "",
                document.getString("question") ?: ""
            )
        }
    }
}