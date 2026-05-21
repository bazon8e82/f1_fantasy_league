package com.example.f1fantasyleague.data.repository

import com.example.f1fantasyleague.data.firestore.COLLECTION_USERS
import com.example.f1fantasyleague.data.firestore.FIELD_EMAIL
import com.example.f1fantasyleague.data.firestore.FIELD_NAME
import com.example.f1fantasyleague.data.firestore.FIELD_POINTS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    val authState: Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser != null)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email.trim(), password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(name: String, email: String, password: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
            val userId = result.user?.uid ?: return Result.failure(Exception("User ID not found"))

            val user = mapOf(
                FIELD_NAME to name.trim(),
                FIELD_EMAIL to email.trim(),
                FIELD_POINTS to 0
            )

            db.collection(COLLECTION_USERS)
                .document(userId)
                .set(user)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}