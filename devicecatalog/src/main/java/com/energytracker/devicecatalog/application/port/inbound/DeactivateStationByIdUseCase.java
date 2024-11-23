package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.StationResponseDto;

public interface DeactivateStationByIdUseCase {

    StationResponseDto deactivateStationById(Long stationId);
}
