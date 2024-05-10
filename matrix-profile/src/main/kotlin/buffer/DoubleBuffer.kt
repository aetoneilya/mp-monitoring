package buffer

import java.nio.BufferOverflowException
import kotlin.math.min

class DoubleBuffer : Buffer {
    private val buff: DoubleArray

    constructor(size: Int) : super(size) {
        this.buff = DoubleArray(size)
    }

    constructor(buffer: DoubleBuffer) : super(buffer.size()) {
        buff = buffer.copy()
        mCnt = buff.size
        mEnd = mCnt - 1
    }

    fun addToEnd(`val`: Double) {
        if (mCnt < mSize) {
            buff[mCnt] = `val`
            mEnd = mCnt
        } else {
            mEnd = (mEnd + 1) % mSize
            mStart = (mStart + 1) % mSize
            buff[mEnd] = `val`
        }
        mCnt++
    }

    fun head(): Double {
        return this.get(0)
    }

    fun get(i: Int): Double {
        if (isFull()) {
            if (i < mSize) {
                val ix: Int = (mStart + i) % mSize
                return buff[ix]
            } else {
                throw BufferOverflowException()
            }
        } else {
            return buff[i]
        }
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
