package com.example.f1fantasyleague.data.repository

import android.R.attr.data
import com.example.f1fantasyleague.data.firestore.COLLECTION_MYSTERY_QUESTIONS
import com.example.f1fantasyleague.data.firestore.DOCUMENT_ROUND_PREFIX
import com.example.f1fantasyleague.data.firestore.FIELD_ANSWER
import com.example.f1fantasyleague.data.firestore.FIELD_QUESTION
import com.example.f1fantasyleague.data.firestore.FIELD_ROUND
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt

object MysteryQuestionRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveMysteryQuestion(
        round: String,
        question: String,
        answer: String
    ) {

        val roundDocumentId = "$DOCUMENT_ROUND_PREFIX$round"

        val data = hashMapOf(
            FIELD_ROUND to parseInt(round),
            FIELD_QUESTION to question,
            FIELD_ANSWER to answer.uppercase(),
        )

        firestore
            .collection(COLLECTION_MYSTERY_QUESTIONS)
            .document(roundDocumentId)
            .set(data)
            .await()
    }

    suspend fun getAllMysteryQuestions(): List<List<String>> {
        val snapshot = firestore
            .collection(COLLECTION_MYSTERY_QUESTIONS)
            .get()
            .await()

        return snapshot.documents.map { document ->
            val round = document.id.removePrefix(DOCUMENT_ROUND_PREFIX)
            listOf(
                round,
                document.getString(FIELD_QUESTION) ?: ""
            )
        }
    }
}