package com.machrist.mpmonitoring.metric.mp.windowstatistic

import com.machrist.mpmonitoring.metric.mp.buffer.DoubleRingBuffer
import com.machrist.mpmonitoring.metric.mp.buffer.ObjectRingBuffer
import java.util.function.DoubleFunction
import kotlin.math.max
import kotlin.math.sqrt

class RollingWindowStatistics : DoubleFunction<WindowStatistic> {
    private var dataBuffer: DoubleRingBuffer
    private var statsBuffer: ObjectRingBuffer<WindowStatistic>
    private var n = 0
    private var k = 0.0
    private var ex = 0.0
    private var ex2 = 0.0

    private var totalDataCount: Long = 0
    private var toSkip = 0

    constructor(windowSize: Int, statsBuffer: Array<WindowStatistic>) {
        this.dataBuffer = DoubleRingBuffer(windowSize)
        this.statsBuffer = ObjectRingBuffer(statsBuffer.size)
        statsBuffer.forEach { this.statsBuffer.addToEnd(it) }
    }

    constructor(
        windowSize: Int,
        statsBufferSize: Int,
    ) : this(windowSize, arrayOfNulls<WindowStatistic>(statsBufferSize) as Array<WindowStatistic>)

    constructor(
        stats: RollingWindowStatistics,
        size: Int,
    ) {
        this.ex2 = stats.ex2
        this.ex = stats.ex
        this.k = stats.k
        this.n = stats.n
        this.toSkip = stats.toSkip
        this.totalDataCount = stats.totalDataCount
        this.dataBuffer = DoubleRingBuffer(stats.dataBuffer)

        this.statsBuffer = ObjectRingBuffer(size)

        stats.getStatsBuffer().asSequence()
            .drop(stats.statsBuffer.size() - size)
            .take(size).forEach { statsBuffer.addToEnd(it) }
    }

    override fun apply(value: Double): WindowStatistic {
        var x = value
        totalDataCount++

        if (value.isNaN() || value.isInfinite()) {
            toSkip = windowSize()
            x = 0.0
        } else {
            toSkip--
        }
        if (dataBuffer.isFull()) {
            removeValue(dataBuffer.head())
        }

        dataBuffer.addToEnd(x)
        addValue(x)

        val stat =
            computeStats(
                x,
                getMean(),
                max(0.0, getPopulationVariance()),
                totalDataCount,
                toSkip > 0,
            )
        statsBuffer.addToEnd(stat)
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

    private fun getMean(): Double = k + ex / n

    private fun getPopulationVariance(): Double = sqrt((ex2 - ex * ex / n) / n)

    private fun computeStats(
        x: Double,
        mean: Double,
        stdDev: Double,
        id: Long,
        skip: Boolean,
    ): WindowStatistic = WindowStatistic(x, mean, stdDev, id, skip)

    private fun getDataBuffer(): DoubleRingBuffer = dataBuffer

    fun getStatsBuffer(): ObjectRingBuffer<WindowStatistic> = statsBuffer

    fun x(i: Int): Double = this.getStatsBuffer()[i].x

    fun mean(i: Int): Double = this.getStatsBuffer()[shiftIndex(i)].mean

    fun stdDev(i: Int): Double = getStatsBuffer()[shiftIndex(i)].stdDev

    fun skip(i: Int): Boolean = this.getStatsBuffer()[shiftIndex(i)].skip

    fun isReady(): Boolean = getDataBuffer().isFull()

    private fun shiftIndex(i: Int): Int = i + windowSize() - 1

    fun windowSize(): Int = getDataBuffer().getLength()

    fun dataSize(): Int = getStatsBuffer().getLength()

    companion object {
        public fun of(
            x: Array<Double>,
            windowSize: Int,
        ): RollingWindowStatistics {
            val stats = RollingWindowStatistics(windowSize, x.size)
            for (value in x) {
                stats.addValue(value)
            }
            return stats
        }

        public fun of(
            x: DoubleArray,
            windowSize: Int,
        ): RollingWindowStatistics {
            val stats = RollingWindowStatistics(windowSize, x.size)
            for (value in x) {
                stats.addValue(value)
            }
            return stats
        }
    }
}
