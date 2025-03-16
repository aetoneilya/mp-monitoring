package com.machrist.mpmonitoring.model

import com.machrist.mpmonitoring.repository.converter.ChannelTypePostgresConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(schema = "monitoring", name = "notification_channels")
open class NotificationChannel(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_channels_id_gen")
    @SequenceGenerator(
        schema = "monitoring",
        name = "notification_channels_id_gen",
        sequenceName = "notification_channels_seq",
        allocationSize = 1,
    )
    @Column(name = "id", nullable = false)
    open var id: Int? = null,
    @NotNull
    @Convert(converter = ChannelTypePostgresConverter::class)
    @Column(name = "type", nullable = false)
    open var type: ChannelType? = null,
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "address", nullable = false)
    open var address: MutableMap<String, String> = mutableMapOf(),
    @ManyToMany
    @JoinTable(
        schema = "monitoring",
        name = "alerts_notification_channels",
        joinColumns = [JoinColumn(name = "notification_channel_id")],
        inverseJoinColumns = [JoinColumn(name = "alert_id")],
    )
    open var alerts: MutableSet<Alert> = mutableSetOf(),
)
