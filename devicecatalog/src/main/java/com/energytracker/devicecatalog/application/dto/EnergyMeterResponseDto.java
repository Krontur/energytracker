package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.ConnectionType;
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

    private String connectionAddress;

    private String energyMeterType;

    private int referenceVoltage;

    private String connectionType;

    private int maxCurrent;

    private int midApprovalYear;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CalibrationScheduleDto> calibrationSchedules;
}
