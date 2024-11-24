package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.ChannelEntity;

public class ChannelPersistenceMapper {

    public static ChannelResponseDto channelEntityToDto(ChannelEntity channelEntity) {
        return new ChannelResponseDto(
                channelEntity.getId(),
                channelEntity.getChannelName(),
                channelEntity.getChannelNumber(),
                channelEntity.getChannelMode(),
                channelEntity.getChannelLongName(),
                channelEntity.getEnergyUnit().name(),
                channelEntity.getPowerUnit().name(),
                channelEntity.getURatio(),
                channelEntity.getIRatio(),
                channelEntity.getPFactor(),
                channelEntity.getLonSubChannel(),
                channelEntity.getLonIsActive()
        );
    }

}
