package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEnergyMeterRequestPersistenceDto {

    private String SerialNumber;
    private String deviceType;
    private String deviceStatus;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midApprovalYear;

}
