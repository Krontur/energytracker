package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaStationPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StationRepositoryAdapter implements StationRepositoryPort {

    private JpaStationPort jpaStationPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return false;
    }

    @Override
    public StationResponseDto createStation(CreateRequestStationDto createRequestStationDto) {
        return null;
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        return List.of();
    }
}
