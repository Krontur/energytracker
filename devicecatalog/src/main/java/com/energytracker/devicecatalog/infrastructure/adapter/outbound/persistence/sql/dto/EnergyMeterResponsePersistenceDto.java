package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnergyMeterResponsePersistenceDto {

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
    List<CalibrationSchedulePersistenceDto> calibrationSchedules;

}
