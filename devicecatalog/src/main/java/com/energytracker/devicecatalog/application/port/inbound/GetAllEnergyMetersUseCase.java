package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;

import java.util.List;

public interface GetAllEnergyMetersUseCase {

    public List<EnergyMeterResponseDto> getAllEnergyMeters();

}
