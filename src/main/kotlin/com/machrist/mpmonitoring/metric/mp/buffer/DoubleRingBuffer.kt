package com.machrist.mpmonitoring.metric.mp.buffer

import kotlin.math.min

class DoubleRingBuffer : RingBuffer<Double> {
    private val buff: DoubleArray

    constructor(size: Int) : super(size) {
        this.buff = DoubleArray(size)
    }

    constructor(buffer: DoubleRingBuffer) : super(buffer.size()) {
        buff = buffer.copy()
        mCnt = buff.size
        mEnd = mCnt - 1
    }

    override fun set(
        index: Int,
        value: Double,
    ) {
        buff[index] = value
    }

    override fun get(index: Int): Double {
        if (index >= mSize) {
            throw IndexOutOfBoundsException()
        }
        val ix = (mStart + index) % mSize
        return buff[ix]
    }

    private fun copy(copyArray: DoubleArray) {
        for (i in copyArray.indices) {
            copyArray[i] = get(i)
        }
    }

    private fun copy(): DoubleArray {
        val copyArray = DoubleArray(min(mCnt, mSize))
        copy(copyArray)
        return copyArray
    }
}
