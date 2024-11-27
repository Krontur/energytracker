package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.domain.model.*;
import com.energytracker.devicecatalog.domain.model.energymeter.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.energymeter.ConnectionType;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeterType;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.CalibrationScheduleEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.ConnectionTypeEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterTypeEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyMeterPersistenceMapperTest {

    @Test
    public void testEnergyMeterEntityToDomain(){
        EnergyMeterEntity energyMeterEntity = getEnergyMeterEntity();

        EnergyMeter energyMeter = EnergyMeterPersistenceMapper.energyMeterEntityToDomain(energyMeterEntity);

        assertEquals(energyMeterEntity.getId(), energyMeter.getDeviceId());
        assertEquals(energyMeterEntity.getDeviceType().name(), energyMeter.getDeviceType().name());
        assertEquals(energyMeterEntity.getConnectionAddress(), energyMeter.getConnectionAddress());
        assertEquals(energyMeterEntity.getEnergyMeterType().name(), energyMeter.getEnergyMeterType().name());
        assertEquals(energyMeterEntity.getReferenceVoltage(), energyMeter.getReferenceVoltage());
        assertEquals(energyMeterEntity.getConnectionType().name(), energyMeter.getConnectionType().name());
        assertEquals(energyMeterEntity.getMaxCurrent(), energyMeter.getMaxCurrent());
        assertEquals(energyMeterEntity.getMidApprovalYear(), energyMeter.getMidApprovalYear());
        assertEquals(energyMeterEntity.getCreatedAt(), energyMeter.getCreatedAt());
        assertEquals(energyMeterEntity.getUpdatedAt(), energyMeter.getUpdatedAt());
    }

    @Test
    public void testEnergyMeterDomainToEntity(){
        EnergyMeter energyMeter = new EnergyMeter(
                1L,
                "CD345323367",
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                0L,
                DeviceType.valueOf("ENERGY_METER"),
                DeviceStatus.valueOf("IN_STOCK"),
                "asdk2323lkjasf",
                EnergyMeterType.valueOf("DIGITAL"),
                400,
                ConnectionType.valueOf("LON"),
                100,
                2021,
                new ArrayList<CalibrationSchedule>()
        );

        EnergyMeterEntity energyMeterEntity = EnergyMeterPersistenceMapper.energyMeterDomainToEntity(energyMeter);

        assertEquals(energyMeter.getDeviceId(), energyMeterEntity.getId());
        assertEquals(energyMeter.getSerialNumber(), energyMeterEntity.getSerialNumber());
        assertEquals(energyMeter.getDeviceType().name(), energyMeterEntity.getDeviceType().name());
        assertEquals(energyMeter.getConnectionAddress(), energyMeterEntity.getConnectionAddress());
        assertEquals(energyMeter.getEnergyMeterType().name(), energyMeterEntity.getEnergyMeterType().name());
        assertEquals(energyMeter.getReferenceVoltage(), energyMeterEntity.getReferenceVoltage());
        assertEquals(energyMeter.getConnectionType().name(), energyMeterEntity.getConnectionType().name());
        assertEquals(energyMeter.getMaxCurrent(), energyMeterEntity.getMaxCurrent());
        assertEquals(energyMeter.getMidApprovalYear(), energyMeterEntity.getMidApprovalYear());
        assertEquals(energyMeter.getCreatedAt(), energyMeterEntity.getCreatedAt());
        assertEquals(energyMeter.getUpdatedAt(), energyMeterEntity.getUpdatedAt());
    }


    private static EnergyMeterEntity getEnergyMeterEntity() {
        return new EnergyMeterEntity(
                1L,
                "CD345323367",
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                0L,
                DeviceTypeEntity.ENERGY_METER,
                DeviceStatusEntity.IN_STOCK,
                "asdk2323lkjasf",
                EnergyMeterTypeEntity.DIGITAL,
                400,
                ConnectionTypeEntity.LON,
                100,
                2021,
                new ArrayList<CalibrationScheduleEntity>()
        );
    }
}
