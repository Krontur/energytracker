package com.energytracker.devicecatalog.application.port.inbound.channel;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;

public interface UpdateChannelUseCase {
    ChannelResponseDto updateChannel(ChannelResponseDto channelResponseDto);
}
