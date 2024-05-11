package com.machrist.mpmonitoring.metric.mp.matrixprofile

import java.util.Arrays

interface OnlineMatrixProfile : MatrixProfile {
    val offset: Int

    fun extend(newPoints: Int): OnlineMatrixProfile {
        val newSize = profile.size + newPoints
        val newProfile: DoubleArray = profile.copyOf(newSize)
        val newIndexes: IntArray = indexes.copyOf(newSize)
        val newRightProfile = rightProfile?.copyOf(newSize)
        val newLeftProfile = leftProfile?.copyOf(newSize)
        val newRightIndexes = rightIndexes?.copyOf(newSize)
        val newLeftIndexes = leftIndexes?.copyOf(newSize)
        for (i in profile.size until newProfile.size) {
            newProfile[i] = Double.POSITIVE_INFINITY
            newIndexes[i] = -1
            newRightProfile?.set(i, Double.POSITIVE_INFINITY)
            newLeftProfile?.set(i, Double.POSITIVE_INFINITY)
            newRightIndexes?.set(i, -1)
            newLeftIndexes?.set(i, -1)
        }
        return BaseOnlineMatrixProfile(
            offset,
            windowSize,
            exclusionZone,
            newProfile,
            newIndexes,
            newLeftProfile,
            newRightProfile,
            newLeftIndexes,
            newRightIndexes,
        )
    }

    fun offset(offset: Int): OnlineMatrixProfile {
        val size = profile.size
        val newProfile: DoubleArray = Arrays.copyOfRange(profile, offset, size)
        val newIndexes: IntArray = Arrays.copyOfRange(indexes, offset, size)
        val newRightProfile = rightProfile?.let { Arrays.copyOfRange(it, offset, size) }
        val newLeftProfile = leftProfile?.let { Arrays.copyOfRange(it, offset, size) }
        val newRightIndexes = rightIndexes?.let { Arrays.copyOfRange(it, offset, size) }
        val newLeftIndexes = leftIndexes?.let { Arrays.copyOfRange(it, offset, size) }

        for (i in newIndexes.indices) {
            newIndexes[i] -= offset
            newRightIndexes?.set(i, offset)
            newLeftIndexes?.set(i, offset)
        }

        return BaseOnlineMatrixProfile(
            this.offset + offset,
            windowSize,
            exclusionZone,
            newProfile,
            newIndexes,
            newLeftProfile,
            newRightProfile,
            newLeftIndexes,
            newRightIndexes,
        )
    }
}
