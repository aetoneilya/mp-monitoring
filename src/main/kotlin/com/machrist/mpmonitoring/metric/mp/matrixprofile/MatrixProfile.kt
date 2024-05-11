package com.machrist.mpmonitoring.metric.mp.matrixprofile

interface MatrixProfile {
    val profile: DoubleArray

    val indexes: IntArray

    val rightProfile: DoubleArray?

    val rightIndexes: IntArray?

    val leftProfile: DoubleArray?

    val leftIndexes: IntArray?

    val exclusionZone: Double

    val windowSize: Int
}
