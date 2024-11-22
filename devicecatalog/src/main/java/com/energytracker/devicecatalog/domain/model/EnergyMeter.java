package com.energytracker.devicecatalog.domain.model;

import com.energytracker.devicecatalog.application.dto.EnergyMeterRequestDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeter extends Device {

    public EnergyMeter(
            Long deviceId,
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String connectionAddress,
            EnergyMeterType energyMeterType,
            int referenceVoltage,
            ConnectionType connectionType,
            int maxCurrent,
            int midApprovalYear,
            List<CalibrationSchedule> calibrationSchedules
    ) {
        super(deviceId, serialNumber, deviceType, deviceStatus);

        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationSchedules = calibrationSchedules;
    }

    public EnergyMeter(
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String connectionAddress,
            EnergyMeterType energyMeterType,
            int referenceVoltage,
            ConnectionType connectionType,
            int maxCurrent,
            int midApprovalYear
    ) {
        super(serialNumber, deviceType, deviceStatus);

        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
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

    public void deactivate() {
        this.setDeviceStatus(DeviceStatus.DEACTIVATED);
    }

}
