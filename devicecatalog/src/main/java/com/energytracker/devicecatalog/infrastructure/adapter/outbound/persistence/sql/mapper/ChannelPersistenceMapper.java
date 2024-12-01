package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.station.PowerUnit;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;

public class ChannelPersistenceMapper {

    public static Channel channelEntityToDomain(ChannelEntity channelEntity) {
        return new Channel(
                channelEntity.getId(),
                channelEntity.getCreatedAt(),
                channelEntity.getUpdatedAt(),
                channelEntity.getVersion(),
                channelEntity.getChannelName(),
                channelEntity.getChannelNumber(),
                channelEntity.getChannelMode(),
                channelEntity.getChannelLongName(),
                EnergyUnit.valueOf(channelEntity.getEnergyUnit().name()),
                PowerUnit.valueOf(channelEntity.getPowerUnit().name()),
                channelEntity.getURatio(),
                channelEntity.getIRatio(),
                channelEntity.getPFactor(),
                channelEntity.getLonSubChannel(),
                channelEntity.getLonIsActive(),
                channelEntity.getStationId()
        );
    }

    public static ChannelEntity channelDomainToEntity(Channel channel) {
        if (channel.getChannelId() == null ) {
            return new ChannelEntity(
                    channel.getChannelNumber(),
                    channel.getChannelName(),
                    channel.getChannelMode(),
                    channel.getChannelLongName(),
                    channel.getEnergyUnit().name(),
                    channel.getPowerUnit().name(),
                    channel.getURatio(),
                    channel.getIRatio(),
                    channel.getPFactor(),
                    channel.getLonSubChannel(),
                    channel.getLonIsActive()
            );
        }
        return new ChannelEntity(
                channel.getChannelId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                channel.getVersion(),
                channel.getChannelNumber(),
                channel.getChannelName(),
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
