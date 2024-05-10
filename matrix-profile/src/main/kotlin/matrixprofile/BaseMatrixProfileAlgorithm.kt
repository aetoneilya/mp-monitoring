package com.machrist.matrixprofile

import com.machrist.common.EPS
import com.machrist.windowstatistic.RollingWindowStatistics
import com.machrist.windowstatistic.WindowStatistic
import kotlin.math.floor

abstract class BaseMatrixProfileAlgorithm<S : WindowStatistic, M : MatrixProfile>(
    rollingWindowStatistics: RollingWindowStatistics<S>,
    protected val exclusionZone: Double,
) : MatrixProfileAlgorithm<S, M> {
    private val rollingStatistics: RollingWindowStatistics<S> = rollingWindowStatistics

    protected val exclusionZoneSize: Int =
        floor(rollingWindowStatistics.windowSize() * exclusionZone + EPS).toInt()

    override fun rollingStatistics(): RollingWindowStatistics<S> {
        return this.rollingStatistics
    }
}
