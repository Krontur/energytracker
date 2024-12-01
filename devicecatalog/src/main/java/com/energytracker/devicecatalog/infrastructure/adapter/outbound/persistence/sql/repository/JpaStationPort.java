package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaStationPort extends JpaRepository<StationEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

}
