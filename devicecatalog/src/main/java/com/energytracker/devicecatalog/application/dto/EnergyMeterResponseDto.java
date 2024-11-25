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
    private String deviceType;
    private String deviceStatus;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midApprovalYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CalibrationScheduleResponseDto> calibrationSchedules;

    public EnergyMeterResponseDto(
            Long energyMeterId,
            String serialNumber,
            String deviceType,
            String deviceStatus,
            String connectionAddress,
            String energyMeterType,
            int referenceVoltage,
            String connectionType,
            int maxCurrent,
            int midApprovalYear,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.energyMeterId = energyMeterId;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
