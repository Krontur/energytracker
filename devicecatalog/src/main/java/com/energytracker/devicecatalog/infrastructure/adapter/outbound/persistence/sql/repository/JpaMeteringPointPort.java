package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaMeteringPointPort extends JpaRepository<MeteringPointEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE MeteringPointEntity m SET m.locationName = :locationName, m.connectionDescription = :connectionDescription," +
            " m.activeStatus = :activeStatus, m.energyMeterEntity = :energyMeterEntity, m.updatedAt = CURRENT_TIMESTAMP," +
            " m.version = m.version + 1 WHERE m.id = :meteringPointId")
    int updateMeteringPointFields(@Param("meteringPointId") Long meteringPointId,
                            @Param("locationName") String locationName,
                            @Param("connectionDescription") String connectionDescription,
                            @Param("activeStatus") boolean activeStatus,
                            @Param("energyMeterEntity") EnergyMeterEntity energyMeterEntity);

}
