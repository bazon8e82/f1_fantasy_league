package com.example.f1fantasyleague.data.models

import com.google.firebase.Timestamp

data class Race(
    val raceId: Int = 0,
    val raceName: String = "",
    val raceDate: Timestamp? = null
)
