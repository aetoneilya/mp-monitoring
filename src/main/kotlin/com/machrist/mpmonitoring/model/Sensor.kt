package com.machrist.mpmonitoring.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(schema = "monitoring", name = "sensors")
open class Sensor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensors_id_gen")
    @SequenceGenerator(name = "sensors_id_gen", schema = "monitoring", sequenceName = "sensors_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    open var id: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    open var project: Project? = null,
    @NotNull
    @Column(name = "storage_sensor_name", nullable = false, length = Integer.MAX_VALUE)
    open var storageSensorName: String,
    @OneToMany(mappedBy = "sensor", fetch = FetchType.EAGER)
    open var labels: MutableSet<Label> = mutableSetOf(),
) {
    fun buildLabelsMap() = labels.associateBy({ it.labelName }, { it.labelValue })
}
