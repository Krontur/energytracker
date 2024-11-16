package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.EnergyMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEnergyMeterPort extends JpaRepository<EnergyMeterEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

}
