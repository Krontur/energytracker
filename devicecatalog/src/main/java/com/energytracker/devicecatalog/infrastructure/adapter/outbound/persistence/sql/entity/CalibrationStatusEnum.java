package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CalibrationStatusEnum extends BaseEntity {

    @Column(name = "calibration_status", nullable = false, unique = true)
    private String calibrationStatus;

}
