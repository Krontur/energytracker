package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;

public interface GetEnergyMeterByIdUseCase {

    EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId);

}
