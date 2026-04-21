package com.example.f1fantasyleague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.f1fantasyleague.ui.theme.F1FantasyLeagueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1FantasyLeagueTheme {
                App()
            }
        }
    }
}