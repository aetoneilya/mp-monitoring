package com.machrist.mpmonitoring.metric.mp.matrixprofile

import com.machrist.mpmonitoring.metric.mp.common.EPS
import com.machrist.mpmonitoring.metric.mp.windowstatistic.RollingWindowStatistics
import kotlin.math.floor

abstract class BaseMatrixProfileAlgorithm<M : MatrixProfile>(
    rollingWindowStatistics: RollingWindowStatistics,
    protected val exclusionZone: Double,
) : MatrixProfileAlgorithm<M> {
    private val rollingStatistics: RollingWindowStatistics = rollingWindowStatistics

    protected val exclusionZoneSize: Int =
        floor(rollingWindowStatistics.windowSize() * exclusionZone + EPS).toInt()

    override fun rollingStatistics(): RollingWindowStatistics {
        return this.rollingStatistics
    }
}
