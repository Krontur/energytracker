package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.port.inbound.*;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyMeterService  implements CreateEnergyMeterUseCase, GetAllEnergyMetersUseCase,
        GetEnergyMeterByIdUseCase, DeleteEnergyMeterByIdUseCase, DeactivateEnergyMeterByIdUseCase {

    private final EnergyMeterRepositoryPort energyMeterRepositoryPort;

    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        return energyMeterRepositoryPort.getAllEnergyMeters();
    };

    @Transactional
    public EnergyMeterResponseDto createEnergyMeter(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeter energyMeter = EnergyMeterMapper.createEnergyMeterRequestDtoToDomain(createEnergyMeterRequestDto);

        if(energyMeterRepositoryPort.existsBySerialNumber(energyMeter.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }

        return energyMeterRepositoryPort.createEnergyMeter(EnergyMeterMapper.createEnergyMeterRequestDomainToDto(energyMeter));

    }

    @Override
    public EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId) {
        return energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
    }

    public void deleteEnergyMeterById(Long energyMeterId) {

        if(energyMeterRepositoryPort.getEnergyMeterById(energyMeterId) == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }
        energyMeterRepositoryPort.deleteEnergyMeterById(energyMeterId);
    }

    @Override
    public EnergyMeterResponseDto deactivateEnergyMeterById(Long energyMeterId) {
        EnergyMeterResponseDto energyMeterResponseDto = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        EnergyMeter energyMeter = EnergyMeterMapper.energyMeterResponseDtoToDomain(energyMeterResponseDto);
        if(energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }
        energyMeter.deactivate();
        System.out.println("EnergyMeterService.deactivateEnergyMeterById: " + energyMeter.getDeviceStatus());
        return energyMeterRepositoryPort.deactivateEnergyMeterById(EnergyMeterMapper.energyMeterRequestDomainToDto(energyMeter));
    }
}
