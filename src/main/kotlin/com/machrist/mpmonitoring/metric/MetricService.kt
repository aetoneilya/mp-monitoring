package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.common.emptyToNull
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Label
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.model.Sensor
import com.machrist.mpmonitoring.repository.LabelRepository
import com.machrist.mpmonitoring.repository.SensorRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class MetricService(
    val metricStorage: MetricStorage,
    val sensorRepository: SensorRepository,
    val labelsRepository: LabelRepository,
) {
    fun findSensorsByLabels(metadata: Map<String, String>) =
        labelsRepository.findByLabelNameIsInAndLabelValueIsIn(metadata.keys, metadata.values)
            .groupBy { it.sensor }
            .filterValues { it.size == metadata.size }
            .filter { sensorToLabels -> sensorToLabels.value.all { metadata[it.labelName] == it.labelValue } }
            .mapNotNull { it.key }
            .toSet()
            .emptyToNull()

    fun createMetric(
        project: Project,
        metadata: Map<String, String>,
    ): Sensor {
        val name = metadata["name"] ?: metadata.entries.first().let { "${it.key}_${it.value}" }
        val numberPostfix =
            sensorRepository.countDistinctByStorageSensorName(name)
                .let { if (it > 0) "_$it" else "" }

        val storageSensorName = "$name$numberPostfix"

        val sensor = Sensor(project = project, storageSensorName = storageSensorName)
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
                metricStorage.getMetric(sensor.project!!.name, sensor.storageSensorName, from, to)
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
        metricStorage.createMetricTable(sensor.project!!.name, sensor.storageSensorName)
        metricStorage.storeMetric(sensor.project!!.name, sensor.storageSensorName, timeSeries)
        return Pair(sensor, timeSeries.size())
    }
}
