package buffer

import java.util.stream.IntStream
import java.util.stream.Stream

class ObjectRingBuffer<T>(
    buffSize: Int,
) : RingBuffer<T>(buffSize) {
    @Suppress("UNCHECKED_CAST")
    private val buff: Array<T?> = arrayOfNulls<Any?>(buffSize) as Array<T?>

    init {
        mCnt = buff.size
        mEnd = mCnt - 1
    }

    override fun set(
        index: Int,
        value: T,
    ) {
        buff[index] = value
    }

    override operator fun get(index: Int): T {
        if (index >= mSize) {
            throw IndexOutOfBoundsException()
        }
        val ix = (mStart + index) % mSize
        return buff[ix]!!
    }
}
