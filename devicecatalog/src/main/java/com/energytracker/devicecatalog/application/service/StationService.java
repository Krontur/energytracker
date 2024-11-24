package com.energytracker.devicecatalog.application.service;


import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.*;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.domain.model.Channel;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.Station;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService implements CreateStationUseCase, GetAllStationsUseCase, GetStationByIdUseCase,
                        DeactivateStationByIdUseCase, DeleteStationByIdUseCase, GetChannelsByStationIdUseCase {

    private final StationRepositoryPort stationRepositoryPort;

    @Override
    @Transactional
    public StationResponseDto createStation(CreateStationRequestDto createStationRequestDto) {
        Station station = StationMapper.createStationRequestDtoToDomain(createStationRequestDto);
        if (stationRepositoryPort.existsBySerialNumber(createStationRequestDto.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        Station createdStation = stationRepositoryPort.createStation(station);
        if (createdStation == null) {
            throw new IllegalStateException("The save operation did not return the created station.");
        }
        return StationMapper.stationResponseDomainToDto(createdStation);
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        List<Station> stations = stationRepositoryPort.getAllStations();
        if (stations.isEmpty()) {
            throw new NotFoundException("No stations found");
        }
        List<StationResponseDto> stationResponseDtos = new ArrayList<StationResponseDto>();
        stations.forEach(station -> {
            stationResponseDtos.add(StationMapper.stationResponseDomainToDto(station));
        });
        return stationResponseDtos;
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        return StationMapper.stationResponseDomainToDto(stationRepositoryPort.getStationById(stationId));
    }

    @Override
    public StationResponseDto deactivateStationById(Long stationId) {
        Station station = stationRepositoryPort.getStationById(stationId);
        if (station == null) {
            throw new NotFoundException("Station with id " + stationId + " not found");
        }
        station.deactivate();
        System.out.println("StationService.deactivateStationById: " + station + " " + station.getDeviceId());
        return StationMapper.stationResponseDomainToDto(stationRepositoryPort.save(station));
    }


    @Override
    public void deleteStationById(Long stationId) {
        stationRepositoryPort.deleteStationById(stationId);
    }

    @Override
    public List<ChannelResponseDto> getChannelsByStationId(Long stationId) {
        List<Channel> channels = stationRepositoryPort.getChannelsByStationId(stationId);
        if (channels.isEmpty()) {
            throw new NotFoundException("No channels found for station with id " + stationId);
        }
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<ChannelResponseDto>();
        channels.forEach(channel -> {
            channelResponseDtos.add(StationMapper.channelDomainToDto(channel));
        });
        return channelResponseDtos;
    }
}
