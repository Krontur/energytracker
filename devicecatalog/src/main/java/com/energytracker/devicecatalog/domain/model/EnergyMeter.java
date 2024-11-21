package com.energytracker.devicecatalog.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeter extends Device {

    public EnergyMeter(
            String serialNumber,
            String deviceType,
            String deviceStatus,
            String connectionAddress,
            String energyMeterType,
            int referenceVoltage,
            String connectionType,
            int maxCurrent,
            int midApprovalYear
    ) {
        super(serialNumber, deviceType, deviceStatus);

        this.connectionAddress = connectionAddress;
        this.energyMeterType = EnergyMeterType.valueOf(energyMeterType);
        this.referenceVoltage = referenceVoltage;
        this.connectionType = ConnectionType.valueOf(connectionType);
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationSchedules = new ArrayList<CalibrationSchedule>();

        addCalibrationSchedule(new CalibrationSchedule(this, 8,
                "First calibration schedule", CalibrationStatus.PENDING));
    }

    private String connectionAddress;

    private EnergyMeterType energyMeterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midApprovalYear;

    private List<CalibrationSchedule> calibrationSchedules;

    public void addCalibrationSchedule(CalibrationSchedule calibrationSchedule) {
        calibrationSchedules.add(calibrationSchedule);
    }

}
