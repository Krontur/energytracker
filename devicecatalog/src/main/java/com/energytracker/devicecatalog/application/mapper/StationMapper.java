package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.*;
import com.energytracker.devicecatalog.domain.model.Channel;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.DeviceType;
import com.energytracker.devicecatalog.domain.model.Station;

import java.util.List;
import java.util.stream.Collectors;

public class StationMapper {
    public static CreateStationRequestDto createRequestStationDomainToDto(Station station) {
        List<ChannelRequestDto> channelRequestDtos = station.getChannelList().stream()
                .map(channel -> new ChannelRequestDto(channel.getChannelName(), channel.getChannelNumber(),
                        channel.getChannelMode(), channel.getChannelLongName(), channel.getEnergyUnit().name(),
                        channel.getPowerUnit().name(), channel.getURatio(), channel.getIRatio(), channel.getPFactor(),
                        channel.getLonSubChannel(), channel.getLonIsActive()))
                .collect(Collectors.toList());

        return new CreateStationRequestDto(
                station.getSerialNumber(),
                station.getDeviceType().name(),
                station.getDeviceStatus().name(),
                station.getStationName(),
                station.getStationType(),
                station.getStationTag(),
                station.getReadingIntervalInSeconds(),
                channelRequestDtos
        );
    };

    public static Station createStationRequestDtoToDomain(CreateStationRequestDto createStationRequestDto) {
        Station station = new Station(
                createStationRequestDto.getSerialNumber(),
                DeviceType.valueOf(createStationRequestDto.getDeviceType()),
                DeviceStatus.valueOf(createStationRequestDto.getDeviceStatus()),
                createStationRequestDto.getStationName(),
                createStationRequestDto.getStationType(),
                createStationRequestDto.getStationTag(),
                createStationRequestDto.getReadingIntervalInSeconds()
        );
        System.out.println("StationMapper.createStationRequestDtoToDomain: " + station);
        return station;
    }

    public static Station stationResponseDtoToDomain(StationResponseDto stationResponseDto) {
        Station station = new Station(
                stationResponseDto.getSerialNumber(),
                DeviceType.valueOf(stationResponseDto.getDeviceType()),
                DeviceStatus.valueOf(stationResponseDto.getDeviceStatus()),
                stationResponseDto.getStationName(),
                stationResponseDto.getStationType(),
                stationResponseDto.getStationTag(),
                stationResponseDto.getReadingIntervalInSeconds()
        );
        System.out.println("StationMapper.stationResponseDtoToDomain: " + station);
        return station;
    }


    public static StationRequestDto stationRequestDomainToDto(Station station) {
        List<ChannelRequestDto> channelRequestDtos = station.getChannelList().stream()
                .map(channel -> new ChannelRequestDto(channel.getChannelName(), channel.getChannelNumber(),
                        channel.getChannelMode(), channel.getChannelLongName(), channel.getEnergyUnit().name(),
                        channel.getPowerUnit().name(), channel.getURatio(), channel.getIRatio(), channel.getPFactor(),
                        channel.getLonSubChannel(), channel.getLonIsActive()))
                .collect(Collectors.toList());
        StationRequestDto stationRequestDto = new StationRequestDto(
                station.getDeviceId(),
                station.getSerialNumber(),
                station.getDeviceType().name(),
                station.getDeviceStatus().name(),
                station.getStationName(),
                station.getStationType(),
                station.getReadingIntervalInSeconds(),
                station.getStationTag(),
                channelRequestDtos
        );
        System.out.println("StationMapper.stationRequestDomainToDto: " + stationRequestDto);
        return stationRequestDto;
    }

    public static StationResponseDto stationResponseDomainToDto(Station station) {
        List<ChannelResponseDto> channelResponseDtos = station.getChannelList().stream()
                .map(channel -> new ChannelResponseDto(channel.getChannelId(), channel.getChannelName(), channel.getChannelNumber(),
                        channel.getChannelMode(), channel.getChannelLongName(), channel.getEnergyUnit().name(),
                        channel.getPowerUnit().name(), channel.getURatio(), channel.getIRatio(), channel.getPFactor(),
                        channel.getLonSubChannel(), channel.getLonIsActive()))
                .collect(Collectors.toList());
        StationResponseDto stationResponseDto = new StationResponseDto(
                station.getDeviceId(),
                station.getCreatedAt(),
                station.getUpdatedAt(),
                station.getSerialNumber(),
                station.getDeviceType().name(),
                station.getDeviceStatus().name(),
                station.getStationName(),
                station.getStationType(),
                station.getReadingIntervalInSeconds(),
                station.getStationTag(),
                channelResponseDtos
        );
        System.out.println("StationMapper.stationRequestDomainToDto: " + stationResponseDto);
        return stationResponseDto;
    }

    public static ChannelResponseDto channelDomainToDto(Channel channel) {
        return new ChannelResponseDto(
                channel.getChannelId(),
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
                channel.getLonIsActive()
        );
    }
}