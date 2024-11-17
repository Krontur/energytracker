package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;

public interface CreateStationUseCase {

    StationResponseDto createStation(CreateStationRequestDto createStationRequestDto);

}
