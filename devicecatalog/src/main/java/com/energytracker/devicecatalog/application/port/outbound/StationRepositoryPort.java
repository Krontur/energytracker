package com.energytracker.devicecatalog.application.port.outbound;


import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StationRepositoryPort {

    boolean existsBySerialNumber(String serialNumber);

    StationResponseDto createStation(CreateRequestStationDto createRequestStationDto);

    List<StationResponseDto> getAllStations();

}
