package com.machrist.matrixprofile

import com.machrist.common.EPS
import com.machrist.common.forwardFft
import com.machrist.common.padSize
import com.machrist.windowstatistic.BaseRollingWindowStatistics
import com.machrist.windowstatistic.BaseWindowStatistic
import com.machrist.windowstatistic.RollingWindowStatistics
import java.util.Arrays
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Real-time lib.STOMP algorithm
 */
class Stompi(
    initialStats: BaseRollingWindowStatistics<BaseWindowStatistic>,
    historySize: Int,
    exclusionZone: Double,
) :
    MatrixProfileAlgorithm<BaseWindowStatistic, OnlineMatrixProfile> {
    private val rollingStatistics = BaseRollingWindowStatistics(initialStats, 1)

    private val history: MutableList<BaseWindowStatistic>

    private var newPoints = 0

    private val historySize: Int
    private val exclusionZoneSize: Int
    private var matrixProfile: OnlineMatrixProfile

    init {
        this.matrixProfile =
            BaseOnlineMatrixProfile(
                Stomp(initialStats, exclusionZone).get()!!,
            )
        this.history =
            initialStats.getStatsBuffer()
                .toStream()
                .limit((initialStats.dataSize() - 1).toLong())
                .collect(Collectors.toList())
        this.historySize = historySize
        this.exclusionZoneSize =
            floor(
                initialStats.windowSize() * exclusionZone + EPS,
            ).toInt()
    }

    constructor(
        initialStats: BaseRollingWindowStatistics<BaseWindowStatistic>,
        historySize: Int,
    ) : this(initialStats, historySize, 0.5)


    override fun rollingStatistics(): RollingWindowStatistics<BaseWindowStatistic> = this.rollingStatistics

    override fun update(value: Double) {
        newPoints++
        history.add(rollingStatistics().getStatsBuffer().get(0))
        super.update(value)
    }

    override fun get(): OnlineMatrixProfile {
        if (this.newPoints > 0) {
            val winSize: Int = rollingStatistics().windowSize()
            val newBuffer =
                Stream.concat(
                    history.stream(),
                    rollingStatistics().getStatsBuffer().toStream(),
                ).toList().toTypedArray()

            val newStats = BaseRollingWindowStatistics(winSize, newBuffer)
            val qIndex = newBuffer.size - rollingStatistics().windowSize() - this.newPoints + 1
            val fft = forwardFft(newStats, false, 0, padSize(newBuffer.size))
            var firstProduct: DoubleArray? = null
            var lastProduct: DoubleArray? = null
            var distProfile: DoubleArray? = null
            var newMatrixProfile = OnlineMatrixProfile.extend(this.matrixProfile, newPoints)
            if (this.newPoints > 1) {
                val firstQuery: RollingWindowStatistics<BaseWindowStatistic> = newQuery(newBuffer, 0)
                val firstDistanceProfile =
                    Mass2<BaseWindowStatistic>().apply(
                        DistanceProfileQuery(newStats, firstQuery, 0, winSize, fft, sqrt = false, norm = false),
                    )
                firstProduct =
                    Arrays.stream(firstDistanceProfile.product)
                        .skip((winSize - 1).toLong())
                        .limit(newMatrixProfile.profile().size.toLong())
                        .toArray()
            }
            var dropValue = 0.0
            for (i in 0 until this.newPoints) {
                val startIdx: Int = qIndex + i
                val query: RollingWindowStatistics<BaseWindowStatistic> = newQuery(newBuffer, startIdx)
                if (i == 0) {
                    val distanceProfile =
                        Mass2<BaseWindowStatistic>().apply(
                            DistanceProfileQuery(newStats, query, 0, winSize, fft, false, false),
                        )
                    distProfile = distanceProfile.profile
                    lastProduct =
                        Arrays.stream(distanceProfile.product)
                            .skip((winSize - 1).toLong())
                            .limit(distProfile.size.toLong())
                            .toArray()
                } else {
                    var cache = lastProduct!![0]
                    for (j in 1 until newBuffer.size - winSize) {
                        val temp = lastProduct[j]
                        lastProduct[j] = cache - newBuffer[j - 1].x() *
                            dropValue + newBuffer[j + winSize - 1].x() *
                            query.getStatsBuffer().get(winSize - 1).x()
                        distProfile!![j] = computeDistance(j, winSize, lastProduct[j], newStats, query)
                        cache = temp
                    }
                    lastProduct[0] = firstProduct!![startIdx]
                    distProfile!![0] = computeDistance(0, winSize, lastProduct[0], newStats, query)
                }
                dropValue = query.x(0)
                val excSt =
                    max(0.0, ((qIndex + i) - exclusionZoneSize).toDouble()).toInt()
                var min = Double.POSITIVE_INFINITY
                var minIdx = -1
                for (j in distProfile.indices) {
                    if (distProfile[j] < 0) {
                        distProfile[j] = 0.0
                    }
                    if (j >= excSt) {
                        distProfile[j] = Double.POSITIVE_INFINITY
                    } else {
                        distProfile[j] = sqrt(distProfile[j])
                    }
                    // update matrix profile
                    if (distProfile[j] < newMatrixProfile.profile()[j]) {
                        newMatrixProfile.indexes()[j] = startIdx
                        newMatrixProfile.profile()[j] = distProfile[j]
                    }
                    // find min distance and its index
                    if (distProfile[j] < min) {
                        min = distProfile[j]
                        minIdx = j
                    }
                    if (j >= startIdx) {
                        // update left profile
                        if ((newMatrixProfile.leftProfile() != null) && (distProfile[j] < newMatrixProfile.leftProfile()!![j])) {
                            newMatrixProfile.leftProfile()?.set(j, distProfile[j])
                            newMatrixProfile.leftIndexes()?.set(j, startIdx)
                        }
                    } else {
                        // update right profile
                        if (newMatrixProfile.rightProfile() != null && distProfile[j] < newMatrixProfile.rightProfile()!![j]) {
                            newMatrixProfile.rightProfile()?.set(j, distProfile[j])
                            newMatrixProfile.rightIndexes()?.set(j, startIdx)
                        }
                    }
                }
                newMatrixProfile.indexes()[startIdx] = minIdx
                newMatrixProfile.profile()[startIdx] = min
                newMatrixProfile.leftIndexes()?.set(startIdx, minIdx)
                newMatrixProfile.leftProfile()?.set(startIdx, min)
            }
            if (this.historySize > 0) {
                var offset = 0
                while (history.size + rollingStatistics().getStatsBuffer().size() > this.historySize) {
                    history.removeAt(0)
                    offset++
                }
                if (offset > 0) {
                    newMatrixProfile = OnlineMatrixProfile.offset(newMatrixProfile, offset)
                }
            }
            this.matrixProfile = newMatrixProfile
        }
        this.newPoints = 0
        return this.matrixProfile
    }

    private fun computeDistance(
        i: Int,
        winSize: Int,
        lastProduct: Double,
        newStats: RollingWindowStatistics<BaseWindowStatistic>,
        query: RollingWindowStatistics<BaseWindowStatistic>,
    ): Double {
        val q = query.getStatsBuffer().getR(0)
        val dist = (lastProduct - winSize * newStats.mean(i) * q.mean) / (newStats.stdDev(i) * q.stdDev())
        return 2 * (winSize - dist)
    }

    private fun newQuery(
        stats: Array<BaseWindowStatistic>,
        from: Int,
    ): RollingWindowStatistics<BaseWindowStatistic> {
        val array: Array<BaseWindowStatistic> =
            stats.asSequence()
                .drop(from)
                .take(rollingStatistics().windowSize())
                .toList().toTypedArray()

        return BaseRollingWindowStatistics(
            rollingStatistics().windowSize(),
            array,
        )
    }
}
