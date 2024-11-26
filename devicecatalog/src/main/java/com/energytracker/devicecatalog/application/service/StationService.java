package com.energytracker.devicecatalog.application.service;


import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.*;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.domain.model.Channel;
import com.energytracker.devicecatalog.domain.model.Station;
import jakarta.persistence.OptimisticLockException;
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
        Station createdStation;
        if (stationRepositoryPort.existsBySerialNumber(createStationRequestDto.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        try {
            createdStation = stationRepositoryPort.createStation(station);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error creating station");
        } catch (Exception e) {
            throw new RuntimeException("Error creating station");
        }
        if (createdStation == null) {
            throw new IllegalStateException("The save operation did not return the created station.");
        }
        return StationMapper.stationResponseDomainToDto(createdStation);
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        List<Station> stations = stationRepositoryPort.getAllStations();
        List<StationResponseDto> stationResponseDtos = new ArrayList<>();
        if (stations.isEmpty()) {
            return stationResponseDtos;
        }
        stations.forEach(station -> {
            stationResponseDtos.add(StationMapper.stationResponseDomainToDto(station));
        });
        return stationResponseDtos;
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        Station station = stationRepositoryPort.getStationById(stationId);
        if (station != null) {
            return StationMapper.stationResponseDomainToDto(station);
        }
        return null;
    }

    @Override
    public StationResponseDto deactivateStationById(Long stationId) {
        Station station = stationRepositoryPort.getStationById(stationId);
        if (station == null) {
            return null;
        }
        station.deactivate();
        try {
            return StationMapper.stationResponseDomainToDto(stationRepositoryPort.save(station));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deactivating station with id " + stationId + ", entity has been modified");
        } catch (Exception e) {
            throw new NotFoundException("Error deactivating station with id " + stationId);
        }
    }


    @Override
    public void deleteStationById(Long stationId) {
        try {
            stationRepositoryPort.deleteStationById(stationId);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deleting station with id " + stationId + ", entity has been modified");
        } catch (Exception e) {
            throw new NotFoundException("Error deleting station with id " + stationId);
        }
    }

    @Override
    public List<ChannelResponseDto> getChannelsByStationId(Long stationId) {
        List<Channel> channels = stationRepositoryPort.getChannelsByStationId(stationId);
        if (channels.isEmpty()) {
            return null;
        }
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channels.forEach(channel -> {
            channelResponseDtos.add(StationMapper.channelDomainToDto(channel));
        });
        return channelResponseDtos;
    }
}
