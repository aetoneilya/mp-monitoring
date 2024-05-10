package com.machrist.windowstatistic

data class BaseWindowStatistic(
    val x: Double,
    val mean: Double,
    val stdDev: Double,
    val id: Long,
    val skip: Boolean,
) : WindowStatistic {
    override fun x(): Double = x

    override fun mean(): Double = mean

    override fun stdDev(): Double = stdDev

    override fun id(): Long = id

    override fun skip(): Boolean = skip
}
