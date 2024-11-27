package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalibrationScheduleEntity extends BaseEntity {

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private int calibrationFrequencyInYears;

    private String comments;

    @Enumerated(EnumType.STRING)
    private CalibrationStatusEntity calibrationStatus;

}
