package com.energytracker.devicecatalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CalibrationSchedule {

    public CalibrationSchedule (EnergyMeter energyMeter, int calibrationFrequencyInYears,
                                String comments, CalibrationStatus calibrationStatus) {
        if(energyMeter.getCalibrationSchedules().isEmpty()) {
            this.lastCalibrationDate = LocalDate.of(energyMeter.getMidApprovalYear(), 1, 1);
        } else {
            this.lastCalibrationDate = energyMeter.getCalibrationSchedules().get(
                    energyMeter.getCalibrationSchedules().size() - 1).getLastCalibrationDate();
        }
        this.nextCalibrationDate = lastCalibrationDate.plusYears(calibrationFrequencyInYears);
        this.calibrationFrequencyInYears = calibrationFrequencyInYears;
        this.comments = comments;
        this.calibrationStatus = calibrationStatus;

    }

    private Long calibrationId;

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private int calibrationFrequencyInYears;

    private String comments;

    private CalibrationStatus calibrationStatus;

}
