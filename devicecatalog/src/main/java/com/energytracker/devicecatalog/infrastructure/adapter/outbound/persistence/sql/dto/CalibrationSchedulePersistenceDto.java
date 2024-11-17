package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalibrationSchedulePersistenceDto {

    private Long id;
    private Long energyMeterId;
    private LocalDate nextCalibrationDate;
    private LocalDate lastCalibrationDate;
    private String calibrationFrequency;
    private String comments;
    private String calibrationStatus;


}
