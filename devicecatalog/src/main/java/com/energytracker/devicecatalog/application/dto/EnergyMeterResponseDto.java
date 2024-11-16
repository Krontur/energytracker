package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.ConnectionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class EnergyMeterResponseDto {

    private Long deviceId;

    private String serialNumber;

    private String deviceType;

    private String connectionAddress;

    private String meterType;

    private int referenceVoltage;

    private String connectionType;

    private int maxCurrent;

    private int midAprovalYear;

    private List<CalibrationScheduleDto> calibrationSchedules;
}
