package com.machrist.mpmonitoring.metric.mp.common

import com.machrist.mpmonitoring.metric.mp.windowstatistic.RollingWindowStatistics
import org.apache.commons.math3.complex.Complex
import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

val EPS = sqrt(Math.ulp(1.0))

fun forwardFft(
    data: RollingWindowStatistics,
    isQuery: Boolean,
    skip: Long,
    padSize: Int,
): Array<Complex> {
    val padded = DoubleArray(padSize)
    val k = IntArray(1)
    if (isQuery) {
        data.getStatsBuffer()
            .asReversedSequence()
            .drop(skip.toInt())
            .take(data.windowSize())
            .forEach { s -> padded[k[0]++] = s.x }
    } else {
        data.getStatsBuffer()
            .asSequence()
            .forEach { s -> padded[k[0]++] = s.x }
    }
    val transformer = FastFourierTransformer(DftNormalization.STANDARD)
    return transformer.transform(padded, TransformType.FORWARD)
}

fun inverseFft(data: Array<Complex>): Array<Complex> {
    val transformer = FastFourierTransformer(DftNormalization.STANDARD)
    return transformer.transform(data, TransformType.INVERSE)
}

fun padSize(n: Int): Int = 2.0.pow(ceil(ln(n.toDouble()) / ln(2.0))).toInt()
