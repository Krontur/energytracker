package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;

public interface DeactivateEnergyMeterByIdUseCase {

    EnergyMeterResponseDto deactivateEnergyMeterById(Long energyMeterId);

}
