package com.energytracker.devicecatalog.application.port.inbound.energymeter;

import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;

public interface GetEnergyMeterByIdUseCase {

    EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId);
}
