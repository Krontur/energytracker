package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;

public interface DeactivateStationByIdUseCase {

    StationResponseDto deactivateStationById(Long stationId);
}
