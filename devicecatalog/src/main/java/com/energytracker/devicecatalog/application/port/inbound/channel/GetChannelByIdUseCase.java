package com.energytracker.devicecatalog.application.port.inbound.channel;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;

public interface GetChannelByIdUseCase {
    ChannelResponseDto getChannelById(Long channelId);
}
