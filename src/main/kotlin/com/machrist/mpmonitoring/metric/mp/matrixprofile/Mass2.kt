package com.machrist.mpmonitoring.metric.mp.matrixprofile

import com.machrist.mpmonitoring.metric.mp.common.forwardFft
import com.machrist.mpmonitoring.metric.mp.common.inverseFft
import com.machrist.mpmonitoring.metric.mp.common.padSize
import org.apache.commons.math3.complex.Complex
import kotlin.math.sqrt

/**
 * MASS_V2 Mueen's Algorithm for Similarity Search is The Fastest Similarity Search Algorithm for
 * Time Series Subsequences under Euclidean Distance and Correlation Coefficient. Reference: [FastestSimilaritySearch.html](https://www.cs.unm.edu/~mueen/FastestSimilaritySearch.html)
 */
class Mass2 : DistanceProfileFunction {
    override fun apply(dsq: DistanceProfileQuery): DistanceProfile {
        val n = dsq.data.dataSize()
        val m = dsq.windowSize
        val qIndex = dsq.queryIndex
        var dataFft = dsq.dataFft
        if (dataFft == null) {
            val padSize: Int = padSize(n)
            dataFft = forwardFft(dsq.data, false, 0, padSize)
        }
        val skip = dsq.query.dataSize() - (m + qIndex)
        val queryFft = forwardFft(dsq.query, true, skip.toLong(), dataFft.size)

        val prod = Array<Complex>(queryFft.size) { queryFft[it].multiply(dataFft[it]) }
        val inv = inverseFft(prod)
        val z = DoubleArray(inv.size)
        for (i in inv.indices) {
            z[i] = inv[i].real
        }
        val meanB = dsq.query.mean(qIndex)
        val stdDevB = dsq.query.stdDev(qIndex)
        val dist = DoubleArray(n - m + 1)
        val shift: Int = m - 1
        val sqN = sqrt(n.toDouble())
        for (i in shift until n) {
            val meanA = dsq.data.mean(i - shift)
            val stdDevA = dsq.data.stdDev(i - shift)
            var d: Double = 2 * (m - (z[i] - m * meanA * meanB) / (stdDevA * stdDevB))
            if (dsq.sqrt) {
                if (dsq.norm && d < 0) {
                    d = 0.0
                } else {
                    d = sqrt(d)
                    if (dsq.norm) {
                        d /= sqN
                    }
                }
            }
            dist[i - m + 1] = d
        }

        return DistanceProfile(dist, z)
    }
}
