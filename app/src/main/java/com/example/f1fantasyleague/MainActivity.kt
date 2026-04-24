package com.example.f1fantasyleague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.f1fantasyleague.ui.theme.F1FantasyLeagueTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                F1FantasyLeagueTheme {

                    var isLoggedIn by remember { mutableStateOf(false) }

                    if (isLoggedIn) {
                        App() // ili HomeScreen()
                    } else {
                        LoginScreen(
                            onLoginClick = {
                                isLoggedIn = true
                            }
                        )
                    }
                }
            }
        }
    }
