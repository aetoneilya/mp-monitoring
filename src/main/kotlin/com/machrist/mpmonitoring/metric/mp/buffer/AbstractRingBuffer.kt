package com.machrist.mpmonitoring.metric.mp.buffer

import kotlin.math.min

abstract class AbstractRingBuffer<T>(
    protected var bufferSize: Int = 0,
) : RingBuffer<T> {
    protected var count: Int = 0
    protected var start: Int = 0
    protected var end: Int = 0
    override val size: Int
        get() = min(count, bufferSize)

    override fun isFull() = count >= bufferSize

    override fun getLength(): Int = bufferSize

    override fun addToEnd(value: T) {
        if (count < bufferSize) {
            this[count] = value
            end = count
        } else {
            end = (end + 1) % bufferSize
            start = (start + 1) % bufferSize
            this[end] = value
        }
        count++
    }

    override fun head(): T = get(0)

    override fun tail(): T = get(size - 1)

    override fun asSequence(): Sequence<T> = (0..<size).asSequence().map { get(it) }

    override fun asReversedSequence(): Sequence<T> = (size - 1 downTo 0).asSequence().map { get(it) }
}
