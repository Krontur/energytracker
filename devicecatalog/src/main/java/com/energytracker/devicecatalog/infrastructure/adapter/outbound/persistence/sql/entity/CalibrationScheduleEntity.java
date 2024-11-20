package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CalibrationScheduleEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "energy_meter_id")
    private EnergyMeterEntity energyMeterEntity;

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private String calibrationFrequency;

    private String comments;

    @Enumerated(EnumType.STRING)
    private CalibrationStatusEntity calibrationStatus;

}
