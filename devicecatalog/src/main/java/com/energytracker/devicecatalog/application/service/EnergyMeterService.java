package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.port.inbound.energymeter.*;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;
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
        GetEnergyMeterByIdUseCase, DeleteEnergyMeterByIdUseCase, DeactivateEnergyMeterByIdUseCase,
        GetInStockEnergyMetersUseCase, UpdateEnergyMeterUseCase {

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

    @Override
    public List<EnergyMeterResponseDto> getInStockEnergyMeters() {
        List<EnergyMeter> energyMeters = energyMeterRepositoryPort.getInStockEnergyMeters();
        if (energyMeters == null) {
            return null;
        }
        try {
            List<EnergyMeterResponseDto> energyMeterResponseDtos = new ArrayList<EnergyMeterResponseDto>();
            energyMeters.forEach(energyMeter -> {
                energyMeterResponseDtos.add(EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter));
            });
            return energyMeterResponseDtos;
        } catch (Exception e) {
            throw new RuntimeException("Error getting in stock energy meters");
        }
    }

    @Override
    public EnergyMeterResponseDto updateEnergyMeter(EnergyMeterResponseDto energyMeterResponseDto) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterResponseDto.getEnergyMeterId());
        if(energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterResponseDto.getEnergyMeterId() + " not found");
        }
        energyMeter.setDeviceStatus(DeviceStatus.valueOf(energyMeterResponseDto.getDeviceStatus()));
        energyMeter.setMaxCurrent(energyMeterResponseDto.getMaxCurrent());
        energyMeter.setConnectionAddress(energyMeterResponseDto.getConnectionAddress());
        try {
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.save(energyMeter));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error updating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error updating energy meter");
        }
    }
}
