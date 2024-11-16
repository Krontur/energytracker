package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.ConnectionType;

public class EnergyMeterResponseDto {

    private Long deviceId;

    private String serialNumber;

    private String deviceType;

    private Long parentMeterId;

    private String connectionAddress;

    private String meterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midAprovalYear;

    private CalibrationSchedule calibrationSchedule;
}
