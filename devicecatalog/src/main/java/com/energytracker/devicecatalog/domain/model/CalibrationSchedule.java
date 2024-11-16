package com.energytracker.devicecatalog.domain.model;

import java.time.LocalDate;

public class CalibrationSchedule {

    private Long calibrationId;

    private EnergyMeter energyMeter;

    private LocalDate lastCalibrationDate;

    private String comments;

    private CalibrationStatus calibrationStatus;

}
