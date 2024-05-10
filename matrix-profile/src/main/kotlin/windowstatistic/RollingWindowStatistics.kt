package com.machrist.windowstatistic

import buffer.DoubleBuffer
import buffer.ObjBuffer
import java.util.function.DoubleFunction

interface RollingWindowStatistics<S : WindowStatistic> : DoubleFunction<S> {
    fun getStatsBuffer(): ObjBuffer<S>

    fun getDataBuffer(): DoubleBuffer

    fun x(i: Int): Double {
        return this.getStatsBuffer().get(i).x()
    }

    fun mean(i: Int): Double {
        return this.getStatsBuffer().get(shiftIndex(i)).mean()
    }

    fun stdDev(i: Int): Double {
        return this.getStatsBuffer().get(shiftIndex(i)).stdDev()
    }

    fun skip(i: Int): Boolean {
        return this.getStatsBuffer().get(shiftIndex(i)).skip()
    }

    fun isReady(): Boolean {
        return this.getDataBuffer().isFull()
    }

    fun shiftIndex(i: Int): Int {
        return i + windowSize() - 1
    }

    fun windowSize(): Int {
        return this.getDataBuffer().getLength()
    }

    fun dataSize(): Int {
        return this.getStatsBuffer().getLength()
    }
}
