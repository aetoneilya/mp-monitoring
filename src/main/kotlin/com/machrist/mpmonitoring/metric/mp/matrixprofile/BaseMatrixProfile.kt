package com.machrist.mpmonitoring.metric.mp.matrixprofile

data class BaseMatrixProfile(
    override val windowSize: Int,
    override val exclusionZone: Double,
    override val profile: DoubleArray,
    override val indexes: IntArray,
    override val rightProfile: DoubleArray?,
    override val leftProfile: DoubleArray?,
    override val rightIndexes: IntArray?,
    override val leftIndexes: IntArray?,
) : MatrixProfile {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseMatrixProfile

        if (windowSize != other.windowSize) return false
        if (exclusionZone != other.exclusionZone) return false
        if (!profile.contentEquals(other.profile)) return false
        if (!indexes.contentEquals(other.indexes)) return false
        if (!rightProfile.contentEquals(other.rightProfile)) return false
        if (!leftProfile.contentEquals(other.leftProfile)) return false
        if (!rightIndexes.contentEquals(other.rightIndexes)) return false
        if (!leftIndexes.contentEquals(other.leftIndexes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = windowSize
        result = 31 * result + exclusionZone.hashCode()
        result = 31 * result + profile.contentHashCode()
        result = 31 * result + indexes.contentHashCode()
        result = 31 * result + rightProfile.contentHashCode()
        result = 31 * result + leftProfile.contentHashCode()
        result = 31 * result + rightIndexes.contentHashCode()
        result = 31 * result + leftIndexes.contentHashCode()
        return result
    }
}
