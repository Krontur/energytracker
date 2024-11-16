package com.energytracker.devicecatalog.application.port.outbound;

import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EnergyMeterRepositoryPort {

    boolean existsBySerialNumber(String serialNumber);

    EnergyMeterResponseDto createEnergyMeter(CreateRequestEnergyMeterDto createRequestEnergyMeterDto);

    List<EnergyMeterResponseDto> getAllEnergyMeters();

}
