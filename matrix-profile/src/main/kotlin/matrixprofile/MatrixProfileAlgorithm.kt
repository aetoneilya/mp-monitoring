package com.machrist.matrixprofile

import com.machrist.windowstatistic.RollingWindowStatistics
import com.machrist.windowstatistic.WindowStatistic
import java.util.function.Supplier

interface MatrixProfileAlgorithm<S : WindowStatistic, M : MatrixProfile> : Supplier<M?> {
    fun update(value: Double) {
        rollingStatistics().apply(value)
    }

    fun isReady(): Boolean = rollingStatistics().getStatsBuffer().isFull()

    fun rollingStatistics(): RollingWindowStatistics<S>
}
