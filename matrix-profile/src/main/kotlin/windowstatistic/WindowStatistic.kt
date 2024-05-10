package com.machrist.windowstatistic

interface WindowStatistic {
    fun x(): Double

    fun mean(): Double

    fun stdDev(): Double

    fun id(): Long

    fun skip(): Boolean
}
