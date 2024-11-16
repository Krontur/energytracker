package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;

public interface CreateStationUseCase {

    public StationResponseDto createStation(CreateRequestStationDto createRequestStationDto);

}
