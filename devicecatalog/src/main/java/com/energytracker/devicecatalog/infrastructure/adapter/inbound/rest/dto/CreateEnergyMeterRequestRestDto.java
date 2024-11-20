package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEnergyMeterRequestRestDto {

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
