package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CalibrationScheduleEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "energy_meter_id")
    private EnergyMeterEntity energyMeter;

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private String calibrationFrequency;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "calibration_status_id")
    private CalibrationStatusEnum calibrationStatus;

}
