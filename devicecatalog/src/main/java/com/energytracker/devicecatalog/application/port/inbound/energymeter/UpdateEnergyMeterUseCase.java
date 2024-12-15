package com.energytracker.devicecatalog.application.port.inbound.energymeter;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;

public interface UpdateEnergyMeterUseCase {

    EnergyMeterResponseDto updateEnergyMeter(Long energyMeterId, CreateEnergyMeterRequestDto createEnergyMeterRequestDto);

}
