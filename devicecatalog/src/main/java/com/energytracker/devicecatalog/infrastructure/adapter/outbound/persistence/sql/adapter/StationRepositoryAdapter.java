package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.StationEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.StationPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaStationPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StationRepositoryAdapter implements StationRepositoryPort {

    private JpaStationPort jpaStationPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaStationPort.existsBySerialNumber(serialNumber);
    }

    @Override
    public StationResponseDto createStation(CreateStationRequestDto createStationRequestDto) {
        StationEntity stationEntity = StationPersistenceMapper.createStationRequestDtoToEntity(createStationRequestDto);
        StationResponseDto stationResponseDto = StationPersistenceMapper.stationResponseEntityToDto(jpaStationPort.save(stationEntity));
        return stationResponseDto;
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        return List.of();
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        return null;
    }

    @Override
    public void deleteStationById(Long stationId) {

    }

    @Override
    public StationResponseDto deactivateStationById(Long stationId) {
        return null;
    }
}
