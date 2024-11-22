package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.EnergyMeterPersistenceMapper;import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaEnergyMeterPort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EnergyMeterRepositoryAdapter implements EnergyMeterRepositoryPort {

    private JpaEnergyMeterPort jpaEnergyMeterPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaEnergyMeterPort.existsBySerialNumber(serialNumber);
    }
    @Override
    public EnergyMeterResponseDto createEnergyMeter(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.save(
                EnergyMeterPersistenceMapper.createEnergyMeterRequestDtoToEntity(createEnergyMeterRequestDto));
        EnergyMeterResponseDto energyMeterResponseDto = EnergyMeterPersistenceMapper.energyMeterResponseEntityToDto(energyMeterEntity);
        return energyMeterResponseDto;
    }


    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        List<EnergyMeterEntity> energyMeterEntities = jpaEnergyMeterPort.findAll();
        List<EnergyMeterResponseDto> energyMeterEntitiesToResponseDtos = new ArrayList<EnergyMeterResponseDto>();
        energyMeterEntities.forEach(energyMeterEntity -> {
            energyMeterEntitiesToResponseDtos.add(EnergyMeterPersistenceMapper.energyMeterResponseEntityToDto(energyMeterEntity));
        });
        return energyMeterEntitiesToResponseDtos;
    }

    @Override
    public EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId) {
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.findById(energyMeterId).orElseThrow(
                () -> new RuntimeException("Energy Meter with id " + energyMeterId + " not found")
        );
        return EnergyMeterPersistenceMapper.energyMeterResponseEntityToDto(energyMeterEntity);
    }

    @Override
    public void deleteEnergyMeterById(Long energyMeterId) {
        jpaEnergyMeterPort.deleteById(energyMeterId);
    }

    @Override
    public EnergyMeterResponseDto deactivateEnergyMeterById(EnergyMeterRequestDto energyMeterRequestDto) {
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.findById(energyMeterRequestDto.getDeviceId()).orElseThrow(
                () -> new RuntimeException("Energy Meter with id " + energyMeterRequestDto.getDeviceId() + " not found")
        );
        return EnergyMeterPersistenceMapper.energyMeterResponseEntityToDto(jpaEnergyMeterPort.save(energyMeterEntity));
    }
}
