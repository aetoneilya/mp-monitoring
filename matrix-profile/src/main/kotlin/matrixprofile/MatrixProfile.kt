package com.machrist.matrixprofile

interface MatrixProfile {
    fun profile(): DoubleArray

    fun indexes(): IntArray

    fun rightProfile(): DoubleArray?

    fun rightIndexes(): IntArray?

    fun leftProfile(): DoubleArray?

    fun leftIndexes(): IntArray?

    fun exclusionZone(): Double

    fun windowSize(): Int
}
