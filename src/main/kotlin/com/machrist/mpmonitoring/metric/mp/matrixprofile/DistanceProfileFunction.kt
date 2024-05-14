package com.machrist.mpmonitoring.metric.mp.matrixprofile

import com.machrist.mpmonitoring.metric.mp.windowstatistic.RollingWindowStatistics
import org.apache.commons.math3.complex.Complex
import java.util.function.Function

interface DistanceProfileFunction :
    Function<DistanceProfileQuery, DistanceProfile>

data class DistanceProfileQuery(
    val data: RollingWindowStatistics,
    val query: RollingWindowStatistics,
    val queryIndex: Int,
    val windowSize: Int,
    val dataFft: Array<Complex>?,
    val sqrt: Boolean,
    val norm: Boolean,
) {
    constructor(
        ts: RollingWindowStatistics,
        query: RollingWindowStatistics,
        queryIndex: Int,
        windowSize: Int,
        fft: Array<Complex>?,
    ) : this(ts, query, queryIndex, windowSize, fft, true, false)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DistanceProfileQuery

        if (data != other.data) return false
        if (query != other.query) return false
        if (queryIndex != other.queryIndex) return false
        if (windowSize != other.windowSize) return false
        if (dataFft != null) {
            if (other.dataFft == null) return false
            if (!dataFft.contentEquals(other.dataFft)) return false
        } else if (other.dataFft != null) {
            return false
        }
        if (sqrt != other.sqrt) return false
        if (norm != other.norm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + query.hashCode()
        result = 31 * result + queryIndex
        result = 31 * result + windowSize
        result = 31 * result + (dataFft?.contentHashCode() ?: 0)
        result = 31 * result + sqrt.hashCode()
        result = 31 * result + norm.hashCode()
        return result
    }
}

data class DistanceProfile(val profile: DoubleArray, val product: DoubleArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DistanceProfile

        if (!profile.contentEquals(other.profile)) return false
        if (!product.contentEquals(other.product)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profile.contentHashCode()
        result = 31 * result + product.contentHashCode()
        return result
    }
}
