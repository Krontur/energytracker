package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.domain.model.ConnectionType;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;

public class EnergyMeterMapper {
    public static CreateRequestEnergyMeterDto createRequestEnergyMeterDomainToDto(EnergyMeter energyMeter) {
        return new CreateRequestEnergyMeterDto(
                energyMeter.getSerialNumber(),
                energyMeter.getDeviceType().name(),
                energyMeter.getConnectionAddress(),
                energyMeter.getEnergyMeterType(),
                energyMeter.getReferenceVoltage(),
                ConnectionType.valueOf(energyMeter.getConnectionType().name()),
                energyMeter.getMaxCurrent(),
                energyMeter.getMidAprovalYear()
        );
    };
}
