package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class EnergyMeterResponseDto {

    private Long energyMeterId;
    private String serialNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
    private String deviceType;
    private String deviceStatus;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midApprovalYear;
    private List<CalibrationScheduleResponseDto> calibrationSchedules;

    public EnergyMeterResponseDto(
            Long energyMeterId,
            String serialNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version,
            String deviceType,
            String deviceStatus,
            String connectionAddress,
            String energyMeterType,
            int referenceVoltage,
            String connectionType,
            int maxCurrent,
            int midApprovalYear
    ) {
        this.energyMeterId = energyMeterId;
        this.serialNumber = serialNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
    }

}
