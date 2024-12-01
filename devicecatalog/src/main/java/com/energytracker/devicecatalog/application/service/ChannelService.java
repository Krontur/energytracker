package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.mapper.ChannelMapper;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.application.port.inbound.channel.GetChannelByIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.channel.UpdateChannelUseCase;
import com.energytracker.devicecatalog.application.port.outbound.ChannelRepositoryPort;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService implements UpdateChannelUseCase, GetChannelByIdUseCase {

    private final ChannelRepositoryPort channelRepositoryPort;

    public ChannelService(ChannelRepositoryPort channelRepositoryPort) {
        this.channelRepositoryPort = channelRepositoryPort;
    }

    @Override
    public ChannelResponseDto getChannelById(Long channelId) {
        Channel channel = channelRepositoryPort.getChannelById(channelId);
        return ChannelMapper.channelDomainToDto(channel);
    }

    public List<ChannelResponseDto> getAllChannels() {
        List<Channel> channels = channelRepositoryPort.getAllChannels();
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        if (channels == null) {
            return channelResponseDtos;
        }
        channels.forEach(
                channel -> channelResponseDtos.add(ChannelMapper.channelDomainToDto(channel))
        );
        return channelResponseDtos;
    }

    @Override
    public ChannelResponseDto updateChannel(ChannelResponseDto channelResponseDto) {
        Channel channel = ChannelMapper.channelDtoToDomain(channelResponseDto);
        Channel updatedChannel = channelRepositoryPort.updateChannel(channel);
        return ChannelMapper.channelDomainToDto(updatedChannel);
    }
}
