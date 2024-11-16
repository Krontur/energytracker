package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.domain.model.Station;

public class StationMapper {
    public static CreateRequestStationDto createRequestStationDomainToDto(Station station) {
        return new CreateRequestStationDto();
    };
}
