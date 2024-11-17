package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.domain.model.CalibrationStatus;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
public class CalibrationScheduleDto {
    private Long calibrationId;

    private Long energyMeterId;

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private int calibrationFrequencyInYears;

    private String comments;

    private String calibrationStatus;
}
