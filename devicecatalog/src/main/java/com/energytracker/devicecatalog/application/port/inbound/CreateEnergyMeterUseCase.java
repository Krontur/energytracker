package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;

public interface CreateEnergyMeterUseCase {

    public EnergyMeterResponseDto createEnergyMeter(CreateRequestEnergyMeterDto createRequestEnergyMeterDto);

}
