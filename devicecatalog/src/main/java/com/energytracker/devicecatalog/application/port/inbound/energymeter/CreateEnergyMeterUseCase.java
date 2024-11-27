package com.energytracker.devicecatalog.application.port.inbound.energymeter;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;

public interface CreateEnergyMeterUseCase {

    EnergyMeterResponseDto createEnergyMeter(CreateEnergyMeterRequestDto createEnergyMeterRequestDto);

}
