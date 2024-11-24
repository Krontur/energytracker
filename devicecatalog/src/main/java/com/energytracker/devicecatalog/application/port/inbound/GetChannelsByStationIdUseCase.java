package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;

import java.util.List;

public interface GetChannelsByStationIdUseCase {

    List<ChannelResponseDto> getChannelsByStationId(Long stationId);

}
