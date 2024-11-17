package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.StationResponseDto;

import java.util.List;

public interface GetAllStationsUseCase {

    List<StationResponseDto> getAllStations();

}
