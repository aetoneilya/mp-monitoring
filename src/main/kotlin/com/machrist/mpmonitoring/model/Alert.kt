package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Duration
import java.time.Instant

@Entity
@Table(schema = "monitoring", name = "alerts")
open class Alert(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerts_id_gen")
    @SequenceGenerator(name = "alerts_id_gen", schema = "monitoring", sequenceName = "alerts_seq", allocationSize = 1)
    @Column(
        name = "id",
        nullable = false,
    )
    open var id: Int? = null,
    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    open var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    open var project: Project?,
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rule", nullable = false)
    open var rule: Map<String, String>,
    @Column(name = "calculation_interval", columnDefinition = "interval")
    open var calculationInterval: Duration,
    @Column(name = "last_calculation")
    open var lastCalculation: Instant,
    @ManyToMany
    @JoinTable(
        name = "alerts_notification_channels",
        schema = "monitoring",
        joinColumns = [JoinColumn(name = "alert_id")],
        inverseJoinColumns = [JoinColumn(name = "notification_channel_id")],
    )
    open var notificationChannels: MutableSet<NotificationChannel>,
)
