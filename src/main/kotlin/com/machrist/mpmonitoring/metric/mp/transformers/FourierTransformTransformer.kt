package com.machrist.mpmonitoring.metric.mp.transformers

import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class FourierTransformTransformer : TimeSeriesTransformer {
    override val name: String = "fourier-transform"

    override fun transform(
        timeSeries: TimeSeries,
        arguments: List<String>,
    ): TimeSeries {
        val n = timeSeries.size
        val frequencyDomain = MutableList(n) { Complex(0.0, 0.0) }

        for (k in 0 until n) {
            for (t in 0 until n) {
                val angle = 2 * PI * k * t / n
                val complex = Complex(cos(angle), -sin(angle)) * timeSeries[t].value
                frequencyDomain[k] += complex
            }
        }

        return TimeSeries(
            frequencyDomain.mapIndexed { index, complex ->
                TimeSeriesDataPoint(
                    dateTime = timeSeries[index].dateTime,
                    value = complex.magnitude,
                )
            },
        )
    }

    data class Complex(val real: Double, val imaginary: Double) {
        val magnitude: Double
            get() = sqrt(real.pow(2) + imaginary.pow(2))

        operator fun plus(other: Complex): Complex {
            return Complex(real + other.real, imaginary + other.imaginary)
        }

        operator fun times(value: Double): Complex {
            return Complex(real * value, imaginary * value)
        }
    }
}
