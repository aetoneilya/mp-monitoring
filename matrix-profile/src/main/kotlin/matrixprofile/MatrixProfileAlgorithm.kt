package com.machrist.matrixprofile

import com.machrist.windowstatistic.RollingWindowStatistics
import java.util.function.Supplier

interface MatrixProfileAlgorithm<M : MatrixProfile> : Supplier<M?> {
    fun update(value: Double) {
        rollingStatistics().apply(value)
    }

    fun isReady(): Boolean = rollingStatistics().getStatsBuffer().isFull()

    fun rollingStatistics(): RollingWindowStatistics
}
