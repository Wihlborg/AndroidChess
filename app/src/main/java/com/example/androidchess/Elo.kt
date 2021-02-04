package com.example.androidchess

import kotlin.math.pow

class Elo {
    private val kFactor = 20

    /**
     * Method to calculate the new ELO after a game
     * @param eloA Elo rating of Player A before the game
     * @param eloB Elo rating of Player B before the game
     * @param winner 1 if Player A wins, 1/2 for a draw and 0 for a Player B win
     * @return the new rating of Player A
     */
    fun getNewRating(eloA: Double, eloB: Double, winner: Double): Double{
        return eloA + kFactor * (winner - getLogisticCDF(eloA, eloB))
    }

    private fun getLogisticCDF(eloA: Double, eloB: Double):Double{
        val x = eloA-eloB
        val exponent = -(x/100)
        return 1/(1+ (10.0).pow(exponent))
    }
}