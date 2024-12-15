package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaStationPort extends JpaRepository<StationEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

    @Modifying
    @Transactional
    @Query("UPDATE StationEntity s SET s.serialNumber = :serialNumber, s.stationName = :stationName," +
            " s.stationType = :stationType, s.deviceStatus = :deviceStatus, s.stationTag = :stationTag WHERE s.id = :stationId")
    int updateStationFields(@Param("stationId") Long stationId,
                            @Param("serialNumber") String serialNumber,
                            @Param("stationName") String stationName,
                            @Param("stationType") String stationType,
                            @Param("deviceStatus") DeviceStatusEntity deviceStatus,
                            @Param("stationTag") String stationTag);

}
