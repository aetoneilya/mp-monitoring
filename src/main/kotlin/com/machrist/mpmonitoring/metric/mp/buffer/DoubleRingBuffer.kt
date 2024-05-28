package com.machrist.mpmonitoring.metric.mp.buffer

import kotlin.math.min

class DoubleRingBuffer : AbstractRingBuffer<Double> {
    private val buff: DoubleArray

    constructor(size: Int) : super(size) {
        this.buff = DoubleArray(size)
    }

    constructor(buffer: DoubleRingBuffer) : super(buffer.size) {
        buff = buffer.copy()
        count = buff.size
        end = count - 1
    }

    constructor(buffer: RingBuffer<Double>) : super(buffer.size) {
        buff = DoubleArray(buffer.size) { buffer[it] }
        count = buff.size
        end = count - 1
    }

    override fun set(
        index: Int,
        value: Double,
    ) {
        buff[index] = value
    }

    override fun get(index: Int): Double {
        if (index >= bufferSize) {
            throw IndexOutOfBoundsException()
        }
        val ix = (start + index) % bufferSize
        return buff[ix]
    }

    private fun copy(copyArray: DoubleArray) {
        for (i in copyArray.indices) {
            copyArray[i] = get(i)
        }
    }

    private fun copy(): DoubleArray {
        val copyArray = DoubleArray(min(count, bufferSize))
        copy(copyArray)
        return copyArray
    }
}
