package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnergyMeterRequestDto {

    public CreateEnergyMeterRequestDto(String serialNumber, String deviceType,
                                       String connectionAddress, String energyMeterType,
                                       int referenceVoltage, String connectionType,
                                       int maxCurrent, int midApprovalYear) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
    }

    private String serialNumber;

    private String deviceType;

    private String connectionAddress;

    private String energyMeterType;

    private int referenceVoltage;

    private String connectionType;

    private int maxCurrent;

    private int midApprovalYear;

    private List<CalibrationScheduleDto> calibrationSchedules;

}