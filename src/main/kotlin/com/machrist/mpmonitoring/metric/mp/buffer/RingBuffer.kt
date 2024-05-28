package com.machrist.mpmonitoring.metric.mp.buffer

interface RingBuffer<T> {
    val size: Int

    fun isFull(): Boolean

    fun getLength(): Int

    fun addToEnd(value: T)

    operator fun set(
        index: Int,
        value: T,
    )

    operator fun get(index: Int): T

    fun head(): T

    fun tail(): T

    fun asSequence(): Sequence<T>

    fun asReversedSequence(): Sequence<T>
}
