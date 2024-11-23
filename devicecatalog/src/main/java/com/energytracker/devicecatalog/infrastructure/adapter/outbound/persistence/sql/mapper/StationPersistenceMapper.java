package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.ChannelEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceTypeEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.StationEntity;

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
}
