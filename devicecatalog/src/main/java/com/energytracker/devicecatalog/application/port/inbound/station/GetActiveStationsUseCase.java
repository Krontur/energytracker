package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;

import java.util.List;

public interface GetActiveStationsUseCase {

        List<StationResponseDto> getActiveStations();
}
