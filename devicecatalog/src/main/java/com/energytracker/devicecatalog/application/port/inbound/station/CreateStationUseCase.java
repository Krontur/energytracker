package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;

public interface CreateStationUseCase {

    StationResponseDto createStation(CreateStationRequestDto createStationRequestDto);

}
