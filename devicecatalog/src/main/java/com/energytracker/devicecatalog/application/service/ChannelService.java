package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.application.port.outbound.ChannelRepositoryPort;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService {

    private final ChannelRepositoryPort channelRepositoryPort;

    public ChannelService(ChannelRepositoryPort channelRepositoryPort) {
        this.channelRepositoryPort = channelRepositoryPort;
    }

    public ChannelResponseDto getChannelById(Long channelId) {
        Channel channel = channelRepositoryPort.getChannelById(channelId);
        return StationMapper.channelDomainToDto(channel);
    }

    public List<ChannelResponseDto> getAllChannels() {
        List<Channel> channels = channelRepositoryPort.getAllChannels();
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        if (channels == null) {
            return channelResponseDtos;
        }
        channels.forEach(
                channel -> channelResponseDtos.add(StationMapper.channelDomainToDto(channel))
        );
        return channelResponseDtos;
    }

}
