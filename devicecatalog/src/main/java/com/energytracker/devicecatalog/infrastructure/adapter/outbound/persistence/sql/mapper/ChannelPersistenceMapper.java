package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.station.PowerUnit;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;

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

    public static Channel channelEntityToDomain(ChannelEntity channelEntity) {
        return new Channel(
                channelEntity.getChannelNumber(),
                channelEntity.getChannelName(),
                channelEntity.getChannelMode(),
                channelEntity.getChannelLongName(),
                EnergyUnit.valueOf(channelEntity.getEnergyUnit().name()),
                PowerUnit.valueOf(channelEntity.getPowerUnit().name()),
                channelEntity.getURatio(),
                channelEntity.getIRatio(),
                channelEntity.getLonSubChannel(),
                channelEntity.getLonIsActive()
        );
    }
}
