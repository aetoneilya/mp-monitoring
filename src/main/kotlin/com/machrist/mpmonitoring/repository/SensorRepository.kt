package com.machrist.mpmonitoring.repository

import com.machrist.mpmonitoring.model.Sensor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface SensorRepository : JpaRepository<Sensor, Int> {
//    @Query(
//        value = """
//        SELECT s
//            FROM Sensor s
//        JOIN Label l ON s.id = l.sensor.id
//            WHERE (l.labelName, l.labelValue) IN (:labelMap)
//        GROUP BY s.id
//        HAVING COUNT(l) = :#{#labelMap.size()}
//    """
//    )
//    fun findByLabels(@Param("labelMap") labelMap: List<List<String>>): List<Sensor>

}