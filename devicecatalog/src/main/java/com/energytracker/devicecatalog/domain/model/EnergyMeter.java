package com.energytracker.devicecatalog.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeter extends Device {

    public EnergyMeter(
            Long deviceId,
            String serialNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String connectionAddress,
            EnergyMeterType energyMeterType,
            int referenceVoltage,
            ConnectionType connectionType,
            int maxCurrent,
            int midApprovalYear
    ) {
        super(deviceId, serialNumber, createdAt, updatedAt, version, deviceType, deviceStatus);

        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
    }

    public EnergyMeter(
            Long deviceId,
            String serialNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version,
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
        super(deviceId, serialNumber, createdAt, updatedAt, version, deviceType, deviceStatus);

        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationScheduleList = calibrationSchedules;
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
        this.calibrationScheduleList = new ArrayList<CalibrationSchedule>();

        addCalibrationSchedule(new CalibrationSchedule(this, 8,
                "First calibration schedule", CalibrationStatus.PENDING));
    }

    private String connectionAddress;

    private EnergyMeterType energyMeterType;

    private int referenceVoltage;

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midApprovalYear;

    private List<CalibrationSchedule> calibrationScheduleList;

    public void addCalibrationSchedule(CalibrationSchedule calibrationSchedule) {
        calibrationScheduleList.add(calibrationSchedule);
    }

}
