package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto;

import com.energytracker.devicecatalog.domain.model.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRequestEnergyMeterPersistenceDto {

    private String serialNumber;
    private String deviceType;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midAprovalYear;

}
