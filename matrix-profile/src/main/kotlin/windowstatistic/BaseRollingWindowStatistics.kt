package com.machrist.windowstatistic

import buffer.ObjBuffer
import buffer.DoubleBuffer
import kotlin.math.max
import kotlin.math.sqrt

class BaseRollingWindowStatistics<S : WindowStatistic> : RollingWindowStatistics<S> {
    private var dataBuffer: DoubleBuffer
    private var statsBuffer: ObjBuffer<S>
    private var n = 0
    private var k = 0.0
    private var ex = 0.0
    private var ex2 = 0.0

    private var totalDataCount: Long = 0
    private var toSkip = 0

    constructor(windowSize: Int, statsBuffer: Array<S>) {
        this.dataBuffer = DoubleBuffer(windowSize)
        this.statsBuffer = ObjBuffer(statsBuffer.size)
        statsBuffer.forEach { this.statsBuffer.addToEnd(it) }
    }

    constructor(
        windowSize: Int,
        statsBufferSize: Int,
    ) : this(windowSize, arrayOfNulls<WindowStatistic>(statsBufferSize) as Array<S>)

    constructor(
        stats: BaseRollingWindowStatistics<S>,
        size: Int,
    ) {
        this.ex2 = stats.ex2
        this.ex = stats.ex
        this.k = stats.k
        this.n = stats.n
        this.toSkip = stats.toSkip
        this.totalDataCount = stats.totalDataCount
        this.dataBuffer = DoubleBuffer(stats.dataBuffer)

        this.statsBuffer = ObjBuffer(size)

        stats.getStatsBuffer().toStream()
            .skip((stats.statsBuffer.size() - size).toLong())
            .limit(size.toLong()).forEach { statsBuffer.addToEnd(it) }
    }

    override fun apply(value: Double): S {
        var value = value
        totalDataCount++
        if (java.lang.Double.isNaN(value) || java.lang.Double.isInfinite(value)) {
            toSkip = windowSize()
            value = 0.0
        } else {
            toSkip--
        }
        if (dataBuffer.isFull()) {
            this.removeValue(dataBuffer.head())
        }
        dataBuffer.addToEnd(value)
        this.addValue(value)
        val mean = getMean()
        val populationVariance = getPopulationVariance()
        val stat =
            computeStats(
                value,
                mean,
                sqrt(max(0.0, populationVariance)),
                populationVariance,
                totalDataCount,
                toSkip > 0,
            )
        getStatsBuffer().addToEnd(stat)
        return stat
    }

    private fun addValue(value: Double) {
        if (n == 0) {
            k = value
        }
        val diff = value - k
        ex += diff
        ex2 += diff * diff
        n++
    }

    private fun removeValue(value: Double) {
        val diff = value - k
        ex -= diff
        ex2 -= diff * diff
        n--
    }

    private fun computeStats(
        x: Double,
        mean: Double,
        stdDev: Double,
        variance: Double,
        id: Long,
        skip: Boolean,
    ): S {
        return BaseWindowStatistic(x, mean, stdDev, id, skip) as S
    }

    override fun getDataBuffer(): DoubleBuffer {
        return dataBuffer
    }

    override fun getStatsBuffer(): ObjBuffer<S> {
        return statsBuffer
    }

    private fun getMean(): Double {
        return k + ex / n
    }

    private fun getPopulationVariance(): Double {
        return (ex2 - ex * ex / n) / n
    }
}
