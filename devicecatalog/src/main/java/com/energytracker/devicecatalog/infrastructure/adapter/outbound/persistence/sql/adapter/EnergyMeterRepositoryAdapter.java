package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CreateRequestEnergyMeterPersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.EnergyMeterPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaEnergyMeterPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnergyMeterRepositoryAdapter implements EnergyMeterRepositoryPort {

    private JpaEnergyMeterPort jpaEnergyMeterPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaEnergyMeterPort.existsBySerialNumber(serialNumber);
    }

    @Override
    public EnergyMeterResponseDto createEnergyMeter(CreateRequestEnergyMeterDto createRequestEnergyMeterDto) {
        CreateRequestEnergyMeterPersistenceDto createRequestEnergyMeterPersistenceDto =
                EnergyMeterPersistenceMapper.createRequestEnergyMeterDtoToPersistence(createRequestEnergyMeterDto);
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.save(
                EnergyMeterPersistenceMapper.createRequestEnergyMeterPersistenceDtoToEntity(createRequestEnergyMeterPersistenceDto));
        EnergyMeterResponseDto energyMeterResponseDto = EnergyMeterPersistenceMapper.energyMeterEntityToResponseDto(energyMeterEntity);
        return energyMeterResponseDto;
    }


    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        return List.of();
    }
}
