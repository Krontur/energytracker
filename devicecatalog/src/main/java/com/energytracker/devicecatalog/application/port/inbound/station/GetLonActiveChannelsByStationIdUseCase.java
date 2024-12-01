package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;

import java.util.List;

public interface GetLonActiveChannelsByStationIdUseCase {

    List<ChannelResponseDto> getLonActiveChannelsByStationId(Long stationId);
}
