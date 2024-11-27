package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;

public interface GetStationByIdUseCase {

    StationResponseDto getStationById(Long stationId);

}
