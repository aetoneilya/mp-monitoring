package com.machrist.matrixprofile

import com.machrist.common.EPS
import com.machrist.common.forwardFft
import com.machrist.common.padSize
import com.machrist.windowstatistic.BaseRollingWindowStatistics
import com.machrist.windowstatistic.BaseWindowStatistic
import com.machrist.windowstatistic.RollingWindowStatistics
import com.machrist.windowstatistic.WindowStatistic
import java.util.Arrays
import kotlin.math.abs
import kotlin.math.sqrt

class Stomp : BaseMatrixProfileAlgorithm<BaseWindowStatistic, MatrixProfile> {
    constructor(
        rollingWindowStatistics: RollingWindowStatistics<BaseWindowStatistic>,
        exclusionZone: Double
    ) : super(rollingWindowStatistics, exclusionZone)

    constructor(rollingWindowStatistics: RollingWindowStatistics<BaseWindowStatistic>) : super(
        rollingWindowStatistics,
        0.5
    )

    constructor(windowSize: Int, bufferSize: Int) : this(BaseRollingWindowStatistics(windowSize, bufferSize))

    override fun get(): MatrixProfile? {
        if (this.isReady()) {
            return stomp(
                this.rollingStatistics(), null, exclusionZone,
                this.exclusionZoneSize
            )
        }
        return null
    }

    companion object {
        private fun <S : WindowStatistic> stomp(
            ts: RollingWindowStatistics<S>,
            query: RollingWindowStatistics<S>?,
            exclusionZone: Double,
            exclusionZoneSize: Int,
            distFunc: DistanceProfileFunction<S>
        ): MatrixProfile {
            var query: RollingWindowStatistics<S>? = query
            var exclusionZone = exclusionZone
            var exclusionZoneSize = exclusionZoneSize
            val windowSize: Int = ts.windowSize()

            val isJoin = query != null
            if (!isJoin) {
                query = ts
            } else {
                exclusionZone = 0.0
                exclusionZoneSize = 0
            }
            val exZone = exclusionZoneSize
            val dataSize: Int = ts.dataSize()
            val querySize: Int = query!!.dataSize()
            val mpSize = dataSize - windowSize + 1
            val numQueries = querySize - windowSize + 1
            require(querySize <= dataSize) { "Query must be smaller or the same size as reference data." }
            require(windowSize >= 4) { "Window size must be at least 4." }

            val leftMatrixProfile: DoubleArray?
            val rightMatrixProfile: DoubleArray?
            val leftProfileIndex: IntArray?
            val rightProfileIndex: IntArray?

            val matrixProfile = DoubleArray(mpSize)
            val profileIndex = IntArray(mpSize)

            if (isJoin) {
                rightMatrixProfile = null
                leftMatrixProfile = null
                rightProfileIndex = null
                leftProfileIndex = null
            } else {
                leftMatrixProfile = DoubleArray(matrixProfile.size)
                rightMatrixProfile = DoubleArray(matrixProfile.size)
                leftProfileIndex = IntArray(matrixProfile.size)
                rightProfileIndex = IntArray(matrixProfile.size)
            }

            for (i in 0 until mpSize) {
                matrixProfile[i] = Double.POSITIVE_INFINITY
                profileIndex[i] = -1
                if (!isJoin) {
                    rightMatrixProfile!![i] = Double.POSITIVE_INFINITY
                    leftMatrixProfile!![i] = Double.POSITIVE_INFINITY
                    rightProfileIndex!![i] = -1
                    leftProfileIndex!![i] = -1
                }
            }

            val fftTs = forwardFft(ts, false, 0, padSize(dataSize))
            val nn = distFunc.apply(DistanceProfileQuery(ts, query, 0, windowSize, fftTs))
            var rnn = nn
            var fftQuery = fftTs
            if (isJoin) {
                fftQuery = forwardFft(query, false, 0, padSize(querySize))
                rnn = distFunc.apply(
                    DistanceProfileQuery(query, ts, 0, windowSize, fftQuery)
                )
            }

            val firstProduct: DoubleArray =
                Arrays.stream(rnn.product).skip((windowSize - 1).toLong()).limit(numQueries.toLong())
                    .toArray()
            val accumulatedProducts: DoubleArray = Arrays.stream(nn.product).skip((windowSize - 1).toLong())
                .limit(mpSize.toLong()).toArray()
            val distanceProfile: DoubleArray = nn.profile
            var dropValue: Double = query.x(0)

            for (i in 0 until numQueries) {
                if (i > 0) {
                    var prevProd = accumulatedProducts[0]
                    var prod = firstProduct[i]
                    for (j in 1..mpSize) {
                        if (j == 1) {
                            accumulatedProducts[0] = prod
                        }

                        val a = (prod - windowSize * ts.mean(j - 1) * query.mean(i))
                        val b = (ts.stdDev(j - 1) * query.stdDev(i))
                        val dist = 2 * (windowSize - a / b)
                        distanceProfile[j - 1] = sqrt(dist)
                        if (j == mpSize) {
                            break
                        }
                        val currProd = accumulatedProducts[j]
                        val newProd = prevProd -
                                ts.x(j - 1) * dropValue +
                                ts.x(j + windowSize - 1) * query.x(i + windowSize - 1)

                        accumulatedProducts[j] = newProd
                        prevProd = currProd
                        prod = newProd
                    }
                }

                dropValue = query.x(i)

                for (k in 0 until mpSize) {
                    if ((exZone > 0 && abs((k - i).toDouble()) <= exZone) || (ts.stdDev(k) < EPS) || ts.skip(k) || ts.skip(
                            i
                        )
                    ) {
                        distanceProfile[k] = Double.POSITIVE_INFINITY
                    }
                    // normal matrixProfile
                    if (distanceProfile[k] < matrixProfile[k]) {
                        matrixProfile[k] = distanceProfile[k]
                        profileIndex[k] = i
                    }

                    if (!isJoin) {
                        // left matrixProfile
                        if (k >= i && distanceProfile[k] < leftMatrixProfile!![k]) {
                            leftMatrixProfile[k] = distanceProfile[k]
                            leftProfileIndex!![k] = i
                        }

                        // right matrixProfile
                        if (k <= i && distanceProfile[k] < rightMatrixProfile!![k]) {
                            rightMatrixProfile[k] = distanceProfile[k]
                            rightProfileIndex!![k] = i
                        }
                    }
                }
            }

            return BaseMatrixProfile(
                windowSize, exclusionZone, matrixProfile, profileIndex,
                rightMatrixProfile, leftMatrixProfile, rightProfileIndex, leftProfileIndex
            )
        }

        fun <S : WindowStatistic> stomp(
            ts: RollingWindowStatistics<S>,
            query: RollingWindowStatistics<S>?, exclusionZone: Double, exclusionZoneSize: Int
        ): MatrixProfile {
            return stomp(ts, query, exclusionZone, exclusionZoneSize, Mass2())
        }
    }
}
