package com.energytracker.devicecatalog.domain.model;

import java.time.LocalDate;

public class CalibrationSchedule {

    public CalibrationSchedule (EnergyMeter energyMeter, int calibrationFrequencyInYears,
                                String comments, CalibrationStatus calibrationStatus) {
        this.energyMeter = energyMeter;
        if(energyMeter.getCalibrationSchedules().isEmpty()) {
            this.lastCalibrationDate = LocalDate.of(energyMeter.getMidAprovalYear(), 1, 1);
        } else {
            this.lastCalibrationDate = energyMeter.getCalibrationSchedules().getLast().lastCalibrationDate;
        }
        this.calibrationFrequencyInYears = calibrationFrequencyInYears;
        this.comments = comments;
        this.calibrationStatus = calibrationStatus;

    }

    private Long calibrationId;

    private EnergyMeter energyMeter;

    private LocalDate nextCalibrationDate;

    private LocalDate lastCalibrationDate;

    private int calibrationFrequencyInYears;

    private String comments;

    private CalibrationStatus calibrationStatus;

}
