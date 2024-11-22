package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeterRequestDto {

    private Long deviceId;
    private String serialNumber;
    private String deviceType;
    private String deviceStatus;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midApprovalYear;
    private List<CalibrationScheduleRequestDto> calibrationSchedules;

}
