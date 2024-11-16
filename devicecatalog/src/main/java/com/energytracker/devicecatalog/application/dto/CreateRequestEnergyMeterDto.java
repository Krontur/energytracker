package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.domain.model.ConnectionType;
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

    private ConnectionType connectionType;

    private int maxCurrent;

    private int midAprovalYear;

}
