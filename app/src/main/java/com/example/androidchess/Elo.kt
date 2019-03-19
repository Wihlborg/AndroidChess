package com.example.androidchess

import kotlin.math.pow

class Elo {
    val kFactor = 20


    fun getNewRating(eloA: Int, eloB: Int, winner: Int): Double{
        var newRatingA = eloA + kFactor * (winner - getLogisticCDF(eloA, eloB))

        return newRatingA
    }

    fun getLogisticCDF(eloA: Int, eloB: Int):Double{
        var x = eloA-eloB
        var exponent = -(x/100)
        var expected = 1/(1+ (10.0).pow(exponent))
        return expected
    }
}