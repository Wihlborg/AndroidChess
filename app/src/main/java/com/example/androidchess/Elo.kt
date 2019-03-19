package com.example.androidchess

import kotlin.math.pow

class Elo {
    private val kFactor = 20


    fun getNewRating(eloA: Double, eloB: Double, winner: Int): Double{
        var newRatingA = eloA + kFactor * (winner - getLogisticCDF(eloA, eloB))

        return newRatingA
    }

    private fun getLogisticCDF(eloA: Double, eloB: Double):Double{
        var x = eloA-eloB
        var exponent = -(x/100)
        var expected = 1/(1+ (10.0).pow(exponent))
        return expected
    }
}