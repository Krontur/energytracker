package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStationPort extends JpaRepository<StationEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

}
