package buffer

import java.util.stream.IntStream
import java.util.stream.Stream
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

    fun toStream(): Stream<T> =
        IntStream.range(0, size())
            .mapToObj { i -> get(i) }

    fun toStreamReversed(): Stream<T> {
        return IntStream
            .iterate(size() - 1) { i: Int -> i - 1 }
            .limit((size()).toLong())
            .mapToObj { i: Int -> this[i] }
    }
}
