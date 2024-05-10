package com.machrist.matrixprofile

import java.util.Arrays

interface OnlineMatrixProfile : MatrixProfile {
    fun offset(): Int

    companion object {
        fun extend(
            profile: OnlineMatrixProfile,
            newPoints: Int,
        ): OnlineMatrixProfile {
            val newSize = profile.profile().size + newPoints
            val newProfile: DoubleArray = profile.profile().copyOf(newSize)
            val newIndexes: IntArray = profile.indexes().copyOf(newSize)
            val newRightProfile = profile.rightProfile()?.copyOf(newSize)
            val newLeftProfile = profile.leftProfile()?.copyOf(newSize)
            val newRightIndexes = profile.rightIndexes()?.copyOf(newSize)
            val newLeftIndexes = profile.leftIndexes()?.copyOf(newSize)
            for (i in profile.profile().size until newProfile.size) {
                newProfile[i] = Double.POSITIVE_INFINITY
                newIndexes[i] = -1
                newRightProfile?.set(i, Double.POSITIVE_INFINITY)
                newLeftProfile?.set(i, Double.POSITIVE_INFINITY)
                newRightIndexes?.set(i, -1)
                newLeftIndexes?.set(i, -1)
            }
            return BaseOnlineMatrixProfile(
                profile.offset(),
                profile.windowSize(),
                profile.exclusionZone(),
                newProfile,
                newIndexes,
                newLeftProfile,
                newRightProfile,
                newLeftIndexes,
                newRightIndexes,
            )
        }

        fun offset(
            profile: OnlineMatrixProfile,
            offset: Int,
        ): OnlineMatrixProfile {
            val size= profile.profile().size
            val newProfile: DoubleArray = Arrays.copyOfRange(profile.profile(), offset, size)
            val newIndexes: IntArray = Arrays.copyOfRange(profile.indexes(), offset, size)
            val newRightProfile = profile.rightProfile()?.let { Arrays.copyOfRange(it, offset, size) }
            val newLeftProfile = profile.leftProfile()?.let { Arrays.copyOfRange(it, offset, size) }
            val newRightIndexes = profile.rightIndexes()?.let { Arrays.copyOfRange(it, offset, size) }
            val newLeftIndexes = profile.leftIndexes()?.let { Arrays.copyOfRange(it, offset, size) }

            for (i in newIndexes.indices) {
                newIndexes[i] -= offset
                newRightIndexes?.set(i, offset)
                newLeftIndexes?.set(i, offset)
            }

            return BaseOnlineMatrixProfile(
                profile.offset() + offset,
                profile.windowSize(),
                profile.exclusionZone(),
                newProfile,
                newIndexes,
                newLeftProfile,
                newRightProfile,
                newLeftIndexes,
                newRightIndexes,
            )
        }
    }
}
