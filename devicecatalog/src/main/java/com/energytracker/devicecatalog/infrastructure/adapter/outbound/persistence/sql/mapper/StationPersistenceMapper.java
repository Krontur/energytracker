package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import com.energytracker.devicecatalog.domain.model.*;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.station.PowerUnit;
import com.energytracker.devicecatalog.domain.model.station.Station;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.StationEntity;

import java.util.ArrayList;
import java.util.List;

public class StationPersistenceMapper {
    public static StationEntity createStationRequestDtoToEntity(CreateStationRequestDto createStationRequestDto) {
        List<ChannelEntity> channelEntityList = new ArrayList<ChannelEntity>();
        createStationRequestDto.getChannelList().forEach(
                channelRequestDto -> {
                    channelEntityList.add(
                            new ChannelEntity(
                                    channelRequestDto.getChannelNumber(),
                                    channelRequestDto.getChannelName(),
                                    channelRequestDto.getChannelMode(),
                                    channelRequestDto.getChannelLongName(),
                                    channelRequestDto.getEnergyUnit(),
                                    channelRequestDto.getPowerUnit(),
                                    channelRequestDto.getURatio(),
                                    channelRequestDto.getIRatio(),
                                    channelRequestDto.getPFactor(),
                                    channelRequestDto.getLonSubChannel(),
                                    channelRequestDto.getLonIsActive()
                            )
                    );
                }
        );
        return new StationEntity(
                createStationRequestDto.getSerialNumber(),
                DeviceTypeEntity.valueOf(createStationRequestDto.getDeviceType()),
                DeviceStatusEntity.valueOf(createStationRequestDto.getDeviceStatus()),
                createStationRequestDto.getStationName(),
                createStationRequestDto.getStationType(),
                createStationRequestDto.getReadingIntervalInSeconds(),
                createStationRequestDto.getStationTag(),
                channelEntityList

        );
    }

    public static StationResponseDto stationResponseEntityToDto(StationEntity stationEntity) {

        List<ChannelResponseDto> channelResponseDtoList = new ArrayList<ChannelResponseDto>();
        stationEntity.getChannelList().forEach(
                channelEntity -> {
                    channelResponseDtoList.add(
                            new ChannelResponseDto(
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
                            )
                    );
                }
        );

        return new StationResponseDto(
                stationEntity.getId(),
                stationEntity.getCreatedAt(),
                stationEntity.getUpdatedAt(),
                stationEntity.getSerialNumber(),
                stationEntity.getDeviceType().name(),
                stationEntity.getDeviceStatus().name(),
                stationEntity.getStationName(),
                stationEntity.getStationType(),
                stationEntity.getReadingIntervalInSeconds(),
                stationEntity.getStationTag(),
                channelResponseDtoList
        );

    }

    public static StationEntity stationRequestDtoToEntity(StationRequestDto stationRequestDto) {
        List<ChannelEntity> channelEntityList = new ArrayList<ChannelEntity>();
        stationRequestDto.getChannelList().forEach(
                channelRequestDto -> {
                    channelEntityList.add(
                            new ChannelEntity(
                                    channelRequestDto.getChannelNumber(),
                                    channelRequestDto.getChannelName(),
                                    channelRequestDto.getChannelMode(),
                                    channelRequestDto.getChannelLongName(),
                                    channelRequestDto.getEnergyUnit(),
                                    channelRequestDto.getPowerUnit(),
                                    channelRequestDto.getURatio(),
                                    channelRequestDto.getIRatio(),
                                    channelRequestDto.getPFactor(),
                                    channelRequestDto.getLonSubChannel(),
                                    channelRequestDto.getLonIsActive()
                            )
                    );
                }
        );
        return new StationEntity(
                stationRequestDto.getSerialNumber(),
                DeviceTypeEntity.valueOf(stationRequestDto.getDeviceType()),
                DeviceStatusEntity.valueOf(stationRequestDto.getDeviceStatus()),
                stationRequestDto.getStationName(),
                stationRequestDto.getStationType(),
                stationRequestDto.getReadingIntervalInSeconds(),
                stationRequestDto.getStationTag(),
                channelEntityList
        );
    }

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
