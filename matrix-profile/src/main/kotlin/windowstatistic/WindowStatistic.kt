package com.machrist.windowstatistic

data class WindowStatistic(
    val x: Double,
    val mean: Double,
    val stdDev: Double,
    val id: Long,
    val skip: Boolean,
)
