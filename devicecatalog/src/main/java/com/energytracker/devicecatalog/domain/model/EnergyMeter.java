package com.energytracker.devicecatalog.domain.model;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeter extends Device {

    public EnergyMeter(
            String serialNumber,
            DeviceType deviceType,
            String connectionAddress,
            String energyMeterType,
            int referenceVoltage,
            ConnectionType connectionType,
            int maxCurrent,
            int midAprovalYear
    ) {
        super(serialNumber, deviceType);
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midAprovalYear = midAprovalYear;
    }

    private String connectionAddress;

    private String energyMeterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midAprovalYear;

    private CalibrationSchedule calibrationSchedule;

}
