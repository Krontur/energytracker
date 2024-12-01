package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.station.PowerUnit;

public class ChannelMapper {

    public static Channel channelDtoToDomain(ChannelResponseDto channelResponseDto) {
        return new Channel(
                channelResponseDto.getChannelId(),
                channelResponseDto.getCreatedAt(),
                channelResponseDto.getUpdatedAt(),
                channelResponseDto.getVersion(),
                channelResponseDto.getChannelName(),
                channelResponseDto.getChannelNumber(),
                channelResponseDto.getChannelMode(),
                channelResponseDto.getChannelLongName(),
                EnergyUnit.valueOf(channelResponseDto.getEnergyUnit()),
                PowerUnit.valueOf(channelResponseDto.getPowerUnit()),
                channelResponseDto.getURatio(),
                channelResponseDto.getIRatio(),
                channelResponseDto.getPFactor(),
                channelResponseDto.getLonSubChannel(),
                channelResponseDto.getLonIsActive(),
                channelResponseDto.getStationId()
        );

    }

    public static ChannelResponseDto channelDomainToDto(Channel channel) {
        return new ChannelResponseDto(
                channel.getChannelId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                channel.getVersion(),
                channel.getChannelName(),
                channel.getChannelNumber(),
                channel.getChannelMode(),
                channel.getChannelLongName(),
                channel.getEnergyUnit().name(),
                channel.getPowerUnit().name(),
                channel.getURatio(),
                channel.getIRatio(),
                channel.getPFactor(),
                channel.getLonSubChannel(),
                channel.getLonIsActive(),
                channel.getStationId()
        );
    }

}
