package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.MetricProject
import com.machrist.mpmonitoring.metric.model.Sensor
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.metric.storage.MetricStorage
import org.springframework.stereotype.Service

@Service
class MetricService(
    val metricStorage: MetricStorage
) {
    fun getMetrics(project: String, labels: Map<String, String>): Sensor {
        return Sensor("id", labels)
    }

    fun storeMetrics(project: MetricProject, metadata: Map<String, String>, timeSeries: List<TimeSeriesDataPoint>) {

        //find appropriate sensor
        val sensorId = "default"

        metricStorage.storeMetric(project.name, TimeSeries(Sensor(sensorId, metadata), timeSeries))
    }
}