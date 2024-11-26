package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.port.inbound.*;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyMeterService  implements CreateEnergyMeterUseCase, GetAllEnergyMetersUseCase,
        GetEnergyMeterByIdUseCase, DeleteEnergyMeterByIdUseCase, DeactivateEnergyMeterByIdUseCase {

    private final EnergyMeterRepositoryPort energyMeterRepositoryPort;

    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        List<EnergyMeter> energyMeters = energyMeterRepositoryPort.getAllEnergyMeters();
        List<EnergyMeterResponseDto> energyMeterResponseDtos = new ArrayList<EnergyMeterResponseDto>();
        energyMeters.forEach(energyMeter -> {
            energyMeterResponseDtos.add(EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter));
        });
        return energyMeterResponseDtos;
    };

    @Transactional
    public EnergyMeterResponseDto createEnergyMeter(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeter energyMeter = EnergyMeterMapper.createEnergyMeterRequestDtoToDomain(createEnergyMeterRequestDto);
        if (energyMeterRepositoryPort.existsBySerialNumber(energyMeter.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        EnergyMeter createdEnergyMeter;
        try {
            createdEnergyMeter = energyMeterRepositoryPort.createEnergyMeter(energyMeter);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error creating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error creating energy meter");
        }
        return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.createEnergyMeter(createdEnergyMeter));

    }

    @Override
    public EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if (energyMeter != null) {
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter);
        }
        return null;
    }

    public void deleteEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if(energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }
        try {
            energyMeterRepositoryPort.deleteEnergyMeterById(energyMeterId);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deleting energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting energy meter");
        }

    }

    @Override
    public EnergyMeterResponseDto deactivateEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if(energyMeter == null) {
            return null;
        }
        energyMeter.deactivate();
        try {
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.save(energyMeter));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deactivating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error deactivating energy meter");
        }
    }
}
