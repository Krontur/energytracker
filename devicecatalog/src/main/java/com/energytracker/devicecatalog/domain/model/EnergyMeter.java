package com.energytracker.devicecatalog.domain.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
public class EnergyMeter extends Device {

    private Long parentMeterId;

    private String connectionAddress;

    private String meterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midAprovalYear;

    private CalibrationSchedule calibrationSchedule;

}
