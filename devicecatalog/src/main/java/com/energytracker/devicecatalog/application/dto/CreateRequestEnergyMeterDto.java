package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRequestEnergyMeterDto {

    private String serialNumber;

    private String deviceType;

    private String connectionAddress;

    private String energyMeterType;

    private int referenceVoltage;

    private String connectionType;

    private int maxCurrent;

    private int midAprovalYear;

}
