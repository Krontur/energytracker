package com.energytracker.devicecatalog.application.port.inbound.energymeter;

import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;

public interface DeactivateEnergyMeterByIdUseCase {

    EnergyMeterResponseDto deactivateEnergyMeterById(Long energyMeterId);

}
