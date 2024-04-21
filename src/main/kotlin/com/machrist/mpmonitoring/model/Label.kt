package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(schema = "monitoring", name = "labels")
open class Label(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labels_id_gen")
    @SequenceGenerator(name = "labels_id_gen", schema = "monitoring", sequenceName = "labels_seq", allocationSize = 1)
    @ColumnDefault("nextval('monitoring.labels_seq'")
    @Column(name = "id", nullable = false)
    open var id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    open var sensor: Sensor?,
    @NotNull
    @Column(name = "label_name", nullable = false, length = Integer.MAX_VALUE)
    open var labelName: String?,
    @Column(name = "label_value", length = Integer.MAX_VALUE)
    open var labelValue: String?,
)
