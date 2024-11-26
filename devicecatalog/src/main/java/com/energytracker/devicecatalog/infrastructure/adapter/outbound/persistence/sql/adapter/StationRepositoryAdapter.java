package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.domain.model.Channel;
import com.energytracker.devicecatalog.domain.model.Station;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.StationEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.ChannelPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.StationPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaStationPort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class StationRepositoryAdapter implements StationRepositoryPort {

    private JpaStationPort jpaStationPort;

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
        Optional<StationEntity> stationEntity = jpaStationPort.findById(stationId);
        if (stationEntity.isPresent()) {
            return StationPersistenceMapper.stationResponseEntityToDomain(stationEntity.get());
        }
        return null;
    }

    @Override
    public void deleteStationById(Long stationId) {
        jpaStationPort.deleteById(stationId);
    }

    @Override
    public List<Channel> getChannelsByStationId(Long stationId) {
        StationEntity stationEntity = jpaStationPort.findById(stationId).get();
        List<Channel> channelList = new ArrayList<Channel>();
        stationEntity.getChannelList().forEach(channelEntity -> {
                channelList.add(ChannelPersistenceMapper.channelEntityToDomain(channelEntity));
                });
        return channelList;
    }

    @Override
    public Station save(Station station) {
        StationEntity stationEntity = jpaStationPort.save(StationPersistenceMapper.stationToEntity(station));
        return StationPersistenceMapper.stationResponseEntityToDomain(stationEntity);
    }
}
