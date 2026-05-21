package com.example.f1fantasyleague.data.scoring

import kotlin.math.abs

object PointsCalculator {

    fun calculateWeekendPoints(
        predictedQualifyingTop3: List<String>,
        actualQualifyingTop10: List<String>,
        predictedRaceTop3: List<String>,
        actualRaceTop10: List<String>,
        mysteryGuessPoints: Int
    ): Int {
        val qualifyingPoints = calculateQualifyingPoints(
            predictedTop3 = predictedQualifyingTop3,
            actualTop10 = actualQualifyingTop10
        )

        val racePoints = calculateRacePoints(
            predictedTop3 = predictedRaceTop3,
            actualTop10 = actualRaceTop10
        )

        return qualifyingPoints + racePoints + mysteryGuessPoints
    }

    fun calculateQualifyingPoints(
        predictedTop3: List<String>,
        actualTop10: List<String>
    ): Int {
        return calculatePositionPoints(
            predictedTop3 = predictedTop3,
            actualTop10 = actualTop10,
            maxPointsPerDriver = 6
        )
    }

    fun calculateRacePoints(
        predictedTop3: List<String>,
        actualTop10: List<String>
    ): Int {
        return calculatePositionPoints(
            predictedTop3 = predictedTop3,
            actualTop10 = actualTop10,
            maxPointsPerDriver = 8
        )
    }

    private fun calculatePositionPoints(
        predictedTop3: List<String>,
        actualTop10: List<String>,
        maxPointsPerDriver: Int
    ): Int {
        var totalPoints = 0

        predictedTop3.forEachIndexed { predictedIndex, driverCode ->
            val actualIndex = actualTop10.indexOf(driverCode)

            if (actualIndex != -1) {
                val positionDifference = abs(predictedIndex - actualIndex)
                val points = maxPointsPerDriver - positionDifference

                if (points > 0) {
                    totalPoints += points
                }
            }
        }

        return totalPoints
    }
}