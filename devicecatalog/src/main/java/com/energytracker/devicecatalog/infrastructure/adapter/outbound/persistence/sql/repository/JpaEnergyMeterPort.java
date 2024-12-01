package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEnergyMeterPort extends JpaRepository<EnergyMeterEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

    List<EnergyMeterEntity> findByDeviceStatus(@NotNull DeviceStatusEntity deviceStatus);
}
