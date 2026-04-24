package com.example.f1fantasyleague

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.f1fantasyleague.ui.theme.F1FantasyLeagueTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            F1FantasyLeagueTheme {
                var isLoggedIn by remember {
                    mutableStateOf(auth.currentUser != null)
                }

                if (isLoggedIn) {
                    App(
                        onLogout = {
                            auth.signOut()
                            isLoggedIn = false
                        }
                    )
                } else {
                    LoginScreen(
                        onSignInClick = { email, password ->
                            signInUser(email.trim(), password) {
                                isLoggedIn = true
                            }
                        },
                        onSignUpClick = { name, email, password ->
                            signUpUser(name.trim(), email.trim(), password) {
                                isLoggedIn = true
                            }
                        }
                    )
                }
            }
        }
    }

    private fun signInUser(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        val cleanEmail = email.trim()

        if (cleanEmail.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(cleanEmail, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun signUpUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        val cleanName = name.trim()
        val cleanEmail = email.trim()

        if (cleanName.isBlank() || cleanEmail.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(cleanEmail, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                val user = hashMapOf(
                    "name" to cleanName,
                    "email" to cleanEmail,
                    "points" to 0
                )

                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                onSuccess()

                db.collection("users")
                    .document(userId)
                    .set(user)
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }
}