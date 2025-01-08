package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import jakarta.transaction.Transactional;

public interface UpdateStationByStationIdUseCase {
    @Transactional
    StationResponseDto updateStation(Long stationId, CreateStationRequestDto createStationRequestDto);
}
