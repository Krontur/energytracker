package com.energytracker.devicecatalog.application.port.inbound.energymeter;

import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;

import java.util.List;

public interface GetAllEnergyMetersUseCase {

    List<EnergyMeterResponseDto> getAllEnergyMeters();

}
