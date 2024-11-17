package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.domain.model.Station;

public class StationMapper {
    public static CreateStationRequestDto createRequestStationDomainToDto(Station station) {
        return new CreateStationRequestDto();
    };
}
