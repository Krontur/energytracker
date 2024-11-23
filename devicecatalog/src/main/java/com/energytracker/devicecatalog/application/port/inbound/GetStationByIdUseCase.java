package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.StationResponseDto;

public interface GetStationByIdUseCase {

    StationResponseDto getStationById(Long stationId);

}
