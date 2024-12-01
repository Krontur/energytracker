package com.energytracker.devicecatalog.application.port.outbound;

import com.energytracker.devicecatalog.domain.model.station.Channel;

import java.util.List;

public interface ChannelRepositoryPort {

    Channel getChannelById(Long channelId);

    List<Channel> getAllChannels();

    Channel updateChannel(Channel channel);
}
