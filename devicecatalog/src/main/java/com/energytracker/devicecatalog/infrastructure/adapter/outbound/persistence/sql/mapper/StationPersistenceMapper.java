package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.domain.model.*;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.Station;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.StationEntity;

import java.util.ArrayList;
import java.util.List;

public class StationPersistenceMapper {

    public static StationEntity stationToEntity(Station station) {
        List<ChannelEntity> channelEntityList = new ArrayList<ChannelEntity>();
        station.getChannelList().forEach(
                channel -> {
                    channelEntityList.add(
                            new ChannelEntity(
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
                            )
                    );
                }
        );
        return new StationEntity(
                station.getDeviceId(),
                station.getCreatedAt(),
                station.getUpdatedAt(),
                station.getVersion(),
                station.getSerialNumber(),
                DeviceTypeEntity.valueOf(station.getDeviceType().name()),
                DeviceStatusEntity.valueOf(station.getDeviceStatus().name()),
                station.getStationName(),
                station.getStationType(),
                station.getReadingIntervalInSeconds(),
                station.getStationTag(),
                channelEntityList
        );
    }

    public static Station stationResponseEntityToDomain(StationEntity stationEntity) {
        List<Channel> channelList = new ArrayList<Channel>();
        stationEntity.getChannelList().forEach(
                channelEntity -> {
                    channelList.add( ChannelPersistenceMapper.channelEntityToDomain(channelEntity));
                }
        );
        return new Station(
                stationEntity.getId(),
                stationEntity.getCreatedAt(),
                stationEntity.getUpdatedAt(),
                stationEntity.getVersion(),
                stationEntity.getSerialNumber(),
                DeviceType.valueOf(stationEntity.getDeviceType().name()),
                DeviceStatus.valueOf(stationEntity.getDeviceStatus().name()),
                stationEntity.getStationName(),
                stationEntity.getStationType(),
                stationEntity.getStationTag(),
                stationEntity.getReadingIntervalInSeconds(),
                channelList
        );
    }
}
