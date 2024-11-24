package com.energytracker.devicecatalog.application.service;


import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.*;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.Station;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return stationRepositoryPort.createStation(StationMapper.createRequestStationDomainToDto(station));
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        return stationRepositoryPort.getAllStations();
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        return stationRepositoryPort.getStationById(stationId);
    }

    @Override
    public StationResponseDto deactivateStationById(Long stationId) {
        StationResponseDto stationResponseDto = stationRepositoryPort.getStationById(stationId);
        Station station = StationMapper.stationResponseDtoToDomain(stationResponseDto);
        if (station == null) {
            throw new IllegalArgumentException("Station with id " + stationId + " not found");
        }
        station.deactivate();
        return stationRepositoryPort.deactivateStationById(StationMapper.stationRequestDomainToDto(station));
    }


    @Override
    public void deleteStationById(Long stationId) {
        stationRepositoryPort.deleteStationById(stationId);
    }

    @Override
    public List<ChannelResponseDto> getChannelsByStationId(Long stationId) {
        List<ChannelResponseDto> channels = stationRepositoryPort.getChannelsByStationId(stationId);
        if (channels.isEmpty()) {
            throw new IllegalArgumentException("No channels found for station with id " + stationId);
        }
        return channels;
    }
}
