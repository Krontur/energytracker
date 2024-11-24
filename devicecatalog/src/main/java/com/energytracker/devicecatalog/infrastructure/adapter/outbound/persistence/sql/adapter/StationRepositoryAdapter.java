package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.StationEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.ChannelPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.StationPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaStationPort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class StationRepositoryAdapter implements StationRepositoryPort {

    private JpaStationPort jpaStationPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaStationPort.existsBySerialNumber(serialNumber);
    }

    @Override
    public StationResponseDto createStation(CreateStationRequestDto createStationRequestDto) {
        StationEntity stationEntity = StationPersistenceMapper.createStationRequestDtoToEntity(createStationRequestDto);
        StationResponseDto stationResponseDto = StationPersistenceMapper.stationResponseEntityToDto(jpaStationPort.save(stationEntity));
        return stationResponseDto;
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        List<StationEntity> stationEntityList = jpaStationPort.findAll();
        List<StationResponseDto> stationResponseDtoList = new ArrayList<StationResponseDto>();
        stationEntityList.forEach(stationEntity -> {
            stationResponseDtoList.add(StationPersistenceMapper.stationResponseEntityToDto(stationEntity));
        });
        return stationResponseDtoList;
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        Optional<StationEntity> stationEntity = jpaStationPort.findById(stationId);
        if (stationEntity.isEmpty()) {
            return null;
        }
        return StationPersistenceMapper.stationResponseEntityToDto(stationEntity.get());
    }

    @Override
    public void deleteStationById(Long stationId) {
        jpaStationPort.deleteById(stationId);
    }

    @Override
    public StationResponseDto deactivateStationById(StationRequestDto stationRequestDto) {
        StationEntity stationEntity = StationPersistenceMapper.stationRequestDtoToEntity(stationRequestDto);
        if (stationEntity == null) {
            throw new IllegalArgumentException("Station with id " + stationRequestDto.getStationId() + " not found");
        }
        return StationPersistenceMapper.stationResponseEntityToDto(jpaStationPort.save(stationEntity));
    }

    @Override
    public List<ChannelResponseDto> getChannelsByStationId(Long stationId) {
        Optional<StationEntity> stationEntity = jpaStationPort.findById(stationId);
        if (stationEntity.isEmpty()) {
            return null;
        }
        List<ChannelResponseDto> channelResponseDtoList = new ArrayList<ChannelResponseDto>();
        stationEntity.get().getChannelList().forEach(channelEntity -> {
                    channelResponseDtoList.add(ChannelPersistenceMapper.channelEntityToDto(channelEntity));
                });
        return channelResponseDtoList;
    }
}
