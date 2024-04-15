package com.machrist.mpmonitoring.model

import com.machrist.mpmonitoring.repository.converter.DurationToPostgresIntervalConverter
import com.machrist.mpmonitoring.repository.converter.JsonToMapConverter
import jakarta.persistence.*
import java.time.Duration
import java.time.LocalDateTime


@Entity
@Table(name = "alerts", schema = "monitoring")
data class Alert(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    var name: String,

    @Column(name = "project_id", nullable = false)
    var projectId: Int,

    @Convert(converter = JsonToMapConverter::class)
    @Column(columnDefinition = "jsonb", nullable = false)
    var rule: Map<String, String>,

    @Convert(converter = DurationToPostgresIntervalConverter::class)
    @Column(name = "calculation_interval", nullable = false)
    var calculationInterval: Duration,

    @Column(name = "last_calculation", nullable = true)
    var lastCalculation: LocalDateTime?
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    lateinit var project: Project
}




