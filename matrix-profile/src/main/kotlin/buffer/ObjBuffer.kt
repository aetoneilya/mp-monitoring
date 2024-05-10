package buffer

import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.math.max

class ObjBuffer<T>(
    buffSize: Int,
) : Buffer(buffSize) {
    @Suppress("UNCHECKED_CAST")
    private val buff: Array<T?> = arrayOfNulls<Any?>(buffSize) as Array<T?>

    init {
        this.mCnt = buff.size
        this.mEnd = this.mCnt - 1
    }

    fun addToEnd(value: T) {
        if (mCnt < mSize) {
            buff[mCnt] = value
            mEnd = mCnt
        } else {
            mEnd = (mEnd + 1) % mSize
            mStart = (mStart + 1) % mSize
            buff[mEnd] = value
        }
        mCnt++
    }

    fun toStream(): Stream<T> =
        IntStream.range(0, size())
            .mapToObj { i -> get(i) }

    fun toStreamReversed(): Stream<T> {
        val to = size()
        val from = 0
        return IntStream
            .iterate(to - 1) { i: Int -> i - 1 }
            .limit((to - from).toLong())
            .mapToObj { i: Int -> this.get(i) }
    }

    fun get(i: Int): T {
        if (i >= mSize) {
            throw IndexOutOfBoundsException()
        }
        val ix = (mStart + i) % mSize
        return buff[ix]!!
    }

    /**
     * Get reversed
     *
     * @param i index back
     * @return 0.0 in case if value is missing
     */
    fun getR(i: Int): T {
        val t = size() - 1
        if (t < 0 || i > t) {
            throw IndexOutOfBoundsException()
        }
        return get(max(0, t - i))
    }
}
