package com.machrist.mpmonitoring.repository

import com.machrist.mpmonitoring.model.Alert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlertRepository : JpaRepository<Alert, Int> {
    @Query(
        "select * from monitoring.alerts a where (a.last_calculation + a.calculation_interval) < now()",
        nativeQuery = true,
    )
    fun findAlertToCheck(): List<Alert>
}
