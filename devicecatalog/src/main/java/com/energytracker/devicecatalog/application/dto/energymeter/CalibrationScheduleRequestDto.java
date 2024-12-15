package com.energytracker.devicecatalog.application.dto.energymeter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalibrationScheduleRequestDto {

    private LocalDate nextCalibrationDate;
    private LocalDate lastCalibrationDate;
    private int calibrationFrequencyInYears;
    private String comments;
    private String calibrationStatus;

}
