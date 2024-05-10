package com.machrist.matrixprofile

data class BaseMatrixProfile(
    val windowSize: Int,
    val exclusionZone: Double,
    val profile: DoubleArray,
    val indexes: IntArray,
    val rightProfile: DoubleArray?,
    val leftProfile: DoubleArray?,
    val rightIndexes: IntArray?,
    val leftIndexes: IntArray?,
) : MatrixProfile {
    override fun profile(): DoubleArray = profile

    override fun indexes(): IntArray = indexes

    override fun rightProfile(): DoubleArray? = rightProfile

    override fun rightIndexes(): IntArray? = rightIndexes

    override fun leftProfile(): DoubleArray? = leftProfile

    override fun leftIndexes(): IntArray? = leftIndexes

    override fun exclusionZone(): Double = exclusionZone

    override fun windowSize(): Int = windowSize

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
