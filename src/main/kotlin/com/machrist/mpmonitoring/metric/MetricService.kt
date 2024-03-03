package com.machrist.mpmonitoring.metric

import com.machrist.mpmonitoring.metric.model.Sensor

class MetricService {
    fun getMetrics(project: String, service: String, labels: Map<String, String>): Sensor {
        return Sensor("id", labels)
    }

    fun storeMetrics(project: String, service: String, body: Sensor) {

    }
}