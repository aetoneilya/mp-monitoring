package com.machrist.mpmonitoring.domain

import com.machrist.mpmonitoring.common.emptyToNull
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.mp.transformers.TimeSeriesTransformer
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Label
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.model.Sensor
import com.machrist.mpmonitoring.repository.LabelRepository
import com.machrist.mpmonitoring.repository.SensorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class MetricService(
    val metricStorage: MetricStorage,
    val sensorRepository: SensorRepository,
    val labelsRepository: LabelRepository,
    transformers: List<TimeSeriesTransformer>,
) {
    private val nameToTransformer = transformers.associateBy { it.name }

    fun findSensorsByLabels(metadata: Map<String, String>) =
        labelsRepository.findByLabelNameIsInAndLabelValueIsIn(metadata.keys, metadata.values)
            .groupBy { it.sensor }
            .filterValues { it.size == metadata.size }
            .filter { sensorToLabels -> sensorToLabels.value.all { metadata[it.labelName] == it.labelValue } }
            .mapNotNull { it.key }
            .toSet()
            .emptyToNull()

    fun findSensorsByName(name: String) =
        sensorRepository.findBySensorName(name) ?: throw IllegalArgumentException(
            "No label with name $name " +
                "found",
        )

    @Transactional
    fun getMetrics(
        payloads: Map<String, Map<String, String>?>,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): Map<String, TimeSeries> {
        return payloads.map {
            val ts =
                if (it.key == "*" && it.value != null) {
                    getMetricByPayload(it.value!!, from, to)
                } else {
                    getMetricBySensorName(it.key, from, to)
                }

            it.key to transform(ts.first(), it.value?.get("transform"))
        }.associateBy({ it.first }, { it.second })
    }



    fun transform(
        timeSeries: TimeSeries,
        transformQuery: String?,
    ): TimeSeries {
        if (transformQuery == null) {
            return timeSeries
        }

        val functionToArguments =
            transformQuery.split("->").map {
                if (!it.contains("(")) {
                    return@map it to listOf<String>()
                }
                val regex = "^(.+)\\((.*)\\)$".toRegex()
                val matchResult = regex.find(it)

                if (matchResult != null) {
                    val functionName = matchResult.groupValues[1]
                    val arguments = matchResult.groupValues[2].split(",").map { argument -> argument.trim() }
                    functionName to arguments
                } else {
                    throw IllegalArgumentException("Invalid function string format")
                }
            }

        var ts = timeSeries
        for ((functionName, arguments) in functionToArguments) {
            ts = nameToTransformer[functionName]?.transform(ts, arguments) ?: ts
        }

        return ts
    }

    @Transactional
    fun getMetricBySensorName(
        sensorName: String,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): List<TimeSeries> {
        val sensor = sensorRepository.findBySensorName(sensorName)

        return listOf(metricStorage.getMetric(sensor?.project!!.name, sensor.sensorName, from, to))
    }

    @Transactional
    fun getMetricByPayload(
        payload: Map<String, String>,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): List<TimeSeries> {
        val sensors =
            payload
                .flatMap { labelsRepository.findByLabelNameAndLabelValue(it.key, it.value) }
                .map { it.sensor }
                .toSet()

        return sensors.map { metricStorage.getMetric(it?.project!!.name, it.sensorName, from, to) }
    }

    fun createMetric(
        project: Project,
        metadata: Map<String, String>,
    ): Sensor {
        val name = metadata["name"] ?: metadata.entries.first().let { "${it.key}_${it.value}" }
        val numberPostfix =
            sensorRepository.countDistinctBySensorName(name)
                .let { if (it > 0) "_$it" else "" }

        val storageSensorName = "$name$numberPostfix"

        val sensor = Sensor(project = project, sensorName = storageSensorName)
        val labels = metadata.map { Label(sensor = sensor, labelName = it.key, labelValue = it.value) }
        sensorRepository.save(sensor)
        labelsRepository.saveAll(labels)
        return sensor
    }

    fun findMetrics(
        sensors: Iterable<Sensor>,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
    ): Map<Sensor, TimeSeries> {
        val timeSeriesBySensor: MutableMap<Sensor, TimeSeries> = mutableMapOf()
        for (sensor in sensors) {
            timeSeriesBySensor[sensor] =
                metricStorage.getMetric(sensor.project!!.name, sensor.sensorName, from, to)
        }
        return timeSeriesBySensor
    }

    fun findAvailableMetrics(): List<Sensor> {
        return sensorRepository.findAll()
    }

    /**
     * returns created or existing sensor and number of processed metric values
     */
    fun storeMetrics(
        project: Project,
        sensor: Sensor,
        timeSeries: TimeSeries,
    ): Pair<Sensor, Long> {
        metricStorage.createMetricTable(sensor.project!!.name, sensor.sensorName)
        metricStorage.storeMetric(sensor.project!!.name, sensor.sensorName, timeSeries)
        return Pair(sensor, timeSeries.size.toLong())
    }
}
