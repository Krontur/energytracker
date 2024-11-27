package com.energytracker.devicecatalog.application.port.inbound.station;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;

import java.util.List;

public interface GetChannelsByStationIdUseCase {

    List<ChannelResponseDto> getChannelsByStationId(Long stationId);

}
