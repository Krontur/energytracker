package com.energytracker.devicecatalog.application.dto.energymeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CalibrationScheduleRequestDto {

    private LocalDate nextCalibrationDate;
    private LocalDate lastCalibrationDate;
    private int calibrationFrequencyInYears;
    private String comments;
    private String calibrationStatus;

}
