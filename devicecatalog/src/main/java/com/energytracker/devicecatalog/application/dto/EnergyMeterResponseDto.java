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
}
