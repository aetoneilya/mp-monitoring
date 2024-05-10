package buffer

import kotlin.math.min

abstract class Buffer(
    protected var mSize: Int = 0,
) {
    protected var mCnt: Int = 0
    protected var mStart: Int = 0
    protected var mEnd: Int = 0

    fun size() = min(mCnt, mSize)

    fun isFull() = mCnt >= mSize

    fun getLength(): Int = mSize
}
