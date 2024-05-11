package com.machrist.mpmonitoring.metric.mp.buffer

import kotlin.math.min

abstract class RingBuffer<T>(
    protected var mSize: Int = 0,
) {
    protected var mCnt: Int = 0
    protected var mStart: Int = 0
    protected var mEnd: Int = 0

    fun size() = min(mCnt, mSize)

    fun isFull() = mCnt >= mSize

    fun getLength(): Int = mSize

    fun addToEnd(value: T) {
        if (mCnt < mSize) {
            set(mCnt, value)
            mEnd = mCnt
        } else {
            mEnd = (mEnd + 1) % mSize
            mStart = (mStart + 1) % mSize
            set(mEnd, value)
        }
        mCnt++
    }

    abstract operator fun set(
        index: Int,
        value: T,
    )

    abstract operator fun get(index: Int): T

    fun head(): T = get(0)

    fun tail(): T = get(size() - 1)

    fun asSequence(): Sequence<T> = (0..<size()).asSequence().map { get(it) }

    fun asReversedSequence(): Sequence<T> = (size() - 1 downTo 0).asSequence().map { get(it) }
}
