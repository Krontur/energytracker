package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.Station;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.StationEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.ChannelPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.StationPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaStationPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class StationRepositoryAdapter implements StationRepositoryPort {

    private JpaStationPort jpaStationPort;

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaStationPort.existsBySerialNumber(serialNumber);
    }

    @Override
    public Station createStation(Station station) {
        StationEntity stationEntity = StationPersistenceMapper.stationToEntity(station);
        return StationPersistenceMapper.stationResponseEntityToDomain(jpaStationPort.save(stationEntity));
    }

    @Override
    public List<Station> getAllStations() {
        List<StationEntity> stationEntityList = jpaStationPort.findAll();
        List<Station> stationResponseList = new ArrayList<Station>();
        if (stationEntityList.isEmpty()) {
            return stationResponseList;
        }
        stationEntityList.forEach(stationEntity -> {
            stationResponseList.add(StationPersistenceMapper.stationResponseEntityToDomain(stationEntity));
        });
        return stationResponseList;
    }

    @Override
    public Station getStationById(Long stationId) {
        StationEntity stationEntity = jpaStationPort.findById(stationId).orElseThrow(
                () -> new EntityNotFoundException("Station with id " + stationId + " not found")
        );
        if (stationEntity != null) {
            return StationPersistenceMapper.stationResponseEntityToDomain(stationEntity);
        }
        return null;
    }

    @Override
    public void deleteStationById(Long stationId) {
        jpaStationPort.deleteById(stationId);
    }

    @Override
    public List<Channel> getChannelsByStationId(Long stationId) {
        StationEntity stationEntity = jpaStationPort.findById(stationId).orElse(null);
        if (stationEntity == null) {
            return null;
        }
        List<Channel> channelList = new ArrayList<Channel>();
        stationEntity.getChannelList().forEach(channelEntity -> {
            channelList.add(ChannelPersistenceMapper.channelEntityToDomain(channelEntity));
        });
        return channelList;
    }

    @Override
    public Station save(Station station) {
        StationEntity stationEntity = jpaStationPort.saveAndFlush(StationPersistenceMapper.stationToEntity(station));
        return StationPersistenceMapper.stationResponseEntityToDomain(stationEntity);
    }

    @Override
    public Station updateStation(Station station) {
        int rowsAffected = jpaStationPort.updateStationFields(
                station.getDeviceId(),
                station.getSerialNumber(),
                station.getStationName(),
                station.getStationType(),
                DeviceStatusEntity.valueOf(station.getDeviceStatus().name()),
                station.getStationTag()
        );
        if (rowsAffected == 0) {
            return null;
        }
        StationEntity stationEntity = jpaStationPort.findById(station.getDeviceId()).orElse(null);
        if (stationEntity == null) {
            return null;
        }
        return StationPersistenceMapper.stationResponseEntityToDomain(stationEntity);
    }

    @Override
    public List<Channel> getLonActiveChannelsByStationId(Long stationId) {
        StationEntity stationEntity = jpaStationPort.findById(stationId).orElse(null);
        if (stationEntity == null) {
            return null;
        }
        List<Channel> channelList = new ArrayList<Channel>();
        stationEntity.getChannelList().forEach(channelEntity -> {
            if (channelEntity.getLonIsActive()) {
                channelList.add(ChannelPersistenceMapper.channelEntityToDomain(channelEntity));
            }
        });
        return channelList;
    }
}
