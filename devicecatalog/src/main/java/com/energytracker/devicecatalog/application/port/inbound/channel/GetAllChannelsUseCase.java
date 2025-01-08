package com.energytracker.devicecatalog.application.port.inbound.channel;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;

import java.util.List;

public interface GetAllChannelsUseCase {
    List<ChannelResponseDto> getAllChannels();
}
