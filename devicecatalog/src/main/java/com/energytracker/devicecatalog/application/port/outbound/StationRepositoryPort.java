package com.energytracker.devicecatalog.application.port.outbound;


import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StationRepositoryPort {

    boolean existsBySerialNumber(String serialNumber);

    StationResponseDto createStation(CreateStationRequestDto createStationRequestDto);

    List<StationResponseDto> getAllStations();

    StationResponseDto getStationById(Long stationId);

    void deleteStationById(Long stationId);

    StationResponseDto deactivateStationById(Long stationId);
}
