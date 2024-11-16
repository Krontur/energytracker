package com.energytracker.devicecatalog.application.service;


import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.CreateStationUseCase;
import com.energytracker.devicecatalog.application.port.inbound.GetAllStationsUseCase;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService implements CreateStationUseCase, GetAllStationsUseCase {

    private final StationRepositoryPort stationRepositoryPort;

    @Override
    public StationResponseDto createStation(CreateRequestStationDto createRequestStationDto) {
        return null;
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        return List.of();
    }
}
