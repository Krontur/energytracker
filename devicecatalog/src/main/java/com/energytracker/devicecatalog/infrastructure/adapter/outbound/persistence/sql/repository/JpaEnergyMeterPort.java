package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.ConnectionTypeEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterTypeEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaEnergyMeterPort extends JpaRepository<EnergyMeterEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

    List<EnergyMeterEntity> findByDeviceStatus(@NotNull DeviceStatusEntity deviceStatus);

    @Modifying
    @Transactional
    @Query("UPDATE EnergyMeterEntity e SET e.serialNumber = :serialNumber, e.energyMeterType = :energyMeterType," +
            " e.connectionType = :connectionType, e.connectionAddress = :connectionAddress, e.maxCurrent = :maxCurrent," +
            " e.referenceVoltage = :referenceVoltage, e.midApprovalYear = :midApprovalYear, e.deviceStatus = :deviceStatus" +
            " e.updatedAt = CURRENT_TIMESTAMP, e.version = e.version + 1 WHERE e.id = :energyMeterId")
    int updateEnergyMeterFields(Long energyMeterId,
                                String serialNumber,
                                EnergyMeterTypeEntity energyMeterType,
                                ConnectionTypeEntity connectionType,
                                String connectionAddress,
                                int maxCurrent,
                                int referenceVoltage,
                                int midApprovalYear,
                                DeviceStatusEntity deviceStatus);
}
