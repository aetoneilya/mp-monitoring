package com.machrist.mpmonitoring.metric.mp.buffer

class ObjectRingBuffer<T>(
    buffSize: Int,
) : AbstractRingBuffer<T>(buffSize) {
    @Suppress("UNCHECKED_CAST")
    private val buff: Array<T?> = arrayOfNulls<Any?>(buffSize) as Array<T?>

    init {
        count = buff.size
        end = count - 1
    }

    override fun set(
        index: Int,
        value: T,
    ) {
        buff[index] = value
    }

    override operator fun get(index: Int): T {
        if (index >= bufferSize) {
            throw IndexOutOfBoundsException()
        }
        val ix = (start + index) % bufferSize
        return buff[ix]!!
    }
}
