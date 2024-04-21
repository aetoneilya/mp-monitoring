package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.metric.model.from
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import com.machrist.mpmonitoring.model.Label
import com.machrist.mpmonitoring.model.Project
import com.machrist.mpmonitoring.model.Sensor
import com.machrist.mpmonitoring.repository.LabelRepository
import com.machrist.mpmonitoring.repository.SensorRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MetricService(
    val metricStorage: MetricStorage,
    val sensorRepository: SensorRepository,
    val labelsRepository: LabelRepository,
) {
    fun findSensorsByLabels(metadata: Map<String, String>) =
        metadata.asSequence()
            .flatMap { (k, v) -> labelsRepository.findByLabelNameAndLabelValue(k, v) }
            .groupBy { it.sensor }
            .filter { labels -> labels.value.all { metadata[it.labelName] == it.labelValue } }
            .mapNotNull { it.key }

    fun createSensor(
        project: Project,
        metadata: Map<String, String>,
    ): Sensor {
        val sensor = Sensor(project = project, storageSensorName = UUID.randomUUID().toString())
        val labels = metadata.map { Label(sensor = sensor, labelName = it.key, labelValue = it.value) }
        sensorRepository.save(sensor)
        labelsRepository.saveAll(labels)
        return sensor
    }

    /**
     * returns created or existing sensor and number of processed metric values
     */
    fun storeMetrics(
        project: Project,
        sensor: Sensor,
        timeSeries: List<TimeSeriesDataPoint>,
    ): Pair<Sensor, Long> {
        metricStorage.storeMetric(MetricProject.from(project), TimeSeries(sensor.storageSensorName, timeSeries))
        return Pair(sensor, timeSeries.size.toLong())
    }
}
