package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.port.inbound.CreateEnergyMeterUseCase;
import com.energytracker.devicecatalog.application.port.inbound.GetAllEnergyMetersUseCase;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.DeviceType;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyMeterService  implements CreateEnergyMeterUseCase, GetAllEnergyMetersUseCase {

    EnergyMeterRepositoryPort energyMeterRepositoryPort;

    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        return null;
    };

    @Override
    public EnergyMeterResponseDto createEnergyMeter(CreateRequestEnergyMeterDto createRequestEnergyMeterDto) {
        EnergyMeter energyMeter = new EnergyMeter(
                createRequestEnergyMeterDto.getSerialNumber(),
                DeviceType.ENERGY_METER,
                createRequestEnergyMeterDto.getConnectionAddress(),
                createRequestEnergyMeterDto.getEnergyMeterType(),
                createRequestEnergyMeterDto.getReferenceVoltage(),
                createRequestEnergyMeterDto.getConnectionType(),
                createRequestEnergyMeterDto.getMaxCurrent(),
                createRequestEnergyMeterDto.getMidAprovalYear()
        );

        if(energyMeterRepositoryPort.existsBySerialNumber(energyMeter.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }

        return energyMeterRepositoryPort.createEnergyMeter(EnergyMeterMapper.createRequestEnergyMeterDomainToDto(energyMeter));

    }
}
