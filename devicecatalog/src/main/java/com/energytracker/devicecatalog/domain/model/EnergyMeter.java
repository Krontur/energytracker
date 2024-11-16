package com.energytracker.devicecatalog.domain.model;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
        this.calibrationSchedules = new ArrayList<CalibrationSchedule>();

        addCalibrationSchedule(new CalibrationSchedule(this, 8,
                "First calibration schedule", CalibrationStatus.COMPLETED));
    }

    private String connectionAddress;

    private String energyMeterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midAprovalYear;

    private List<CalibrationSchedule> calibrationSchedules;

    public void addCalibrationSchedule(CalibrationSchedule calibrationSchedule) {
        calibrationSchedules.add(calibrationSchedule);
    }

}
