package com.machrist.mpmonitoring.metric.mp.matrixprofile

data class BaseOnlineMatrixProfile(
    override val offset: Int,
    override val windowSize: Int,
    override val exclusionZone: Double,
    override val profile: DoubleArray,
    override val indexes: IntArray,
    override val leftProfile: DoubleArray?,
    override val rightProfile: DoubleArray?,
    override val leftIndexes: IntArray?,
    override val rightIndexes: IntArray?,
) : OnlineMatrixProfile {
    constructor(profile: MatrixProfile) : this(
        0,
        profile.windowSize,
        profile.exclusionZone,
        profile.profile,
        profile.indexes,
        profile.leftProfile,
        profile.rightProfile,
        profile.leftIndexes,
        profile.rightIndexes,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseOnlineMatrixProfile

        if (offset != other.offset) return false
        if (windowSize != other.windowSize) return false
        if (exclusionZone != other.exclusionZone) return false
        if (!profile.contentEquals(other.profile)) return false
        if (!indexes.contentEquals(other.indexes)) return false
        if (leftProfile != null) {
            if (other.leftProfile == null) return false
            if (!leftProfile.contentEquals(other.leftProfile)) return false
        } else if (other.leftProfile != null) {
            return false
        }
        if (rightProfile != null) {
            if (other.rightProfile == null) return false
            if (!rightProfile.contentEquals(other.rightProfile)) return false
        } else if (other.rightProfile != null) {
            return false
        }
        if (leftIndexes != null) {
            if (other.leftIndexes == null) return false
            if (!leftIndexes.contentEquals(other.leftIndexes)) return false
        } else if (other.leftIndexes != null) {
            return false
        }
        if (rightIndexes != null) {
            if (other.rightIndexes == null) return false
            if (!rightIndexes.contentEquals(other.rightIndexes)) return false
        } else if (other.rightIndexes != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = offset
        result = 31 * result + windowSize
        result = 31 * result + exclusionZone.hashCode()
        result = 31 * result + profile.contentHashCode()
        result = 31 * result + indexes.contentHashCode()
        result = 31 * result + (leftProfile?.contentHashCode() ?: 0)
        result = 31 * result + (rightProfile?.contentHashCode() ?: 0)
        result = 31 * result + (leftIndexes?.contentHashCode() ?: 0)
        result = 31 * result + (rightIndexes?.contentHashCode() ?: 0)
        return result
    }
}
