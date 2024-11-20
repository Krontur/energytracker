package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CalibrationSchedulePersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CreateEnergyMeterRequestPersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.EnergyMeterResponsePersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyMeterPersistenceMapperTest {

    @Test
    public void testEnergyMeterResponseFromEntityToPersistenceDto() {
        EnergyMeterEntity energyMeterEntity = getEnergyMeterEntity();

        EnergyMeterResponsePersistenceDto energyMeterResponsePersistenceDto =
                EnergyMeterPersistenceMapper.energyMeterResponseFromEntityToPersistenceDto(energyMeterEntity);

        assertEquals(energyMeterEntity.getId(), energyMeterResponsePersistenceDto.getEnergyMeterId());
        assertEquals(energyMeterEntity.getSerialNumber(), energyMeterResponsePersistenceDto.getSerialNumber());
        assertEquals(energyMeterEntity.getDeviceType().toString(), energyMeterResponsePersistenceDto.getDeviceType());
        assertEquals(energyMeterEntity.getConnectionAddress(), energyMeterResponsePersistenceDto.getConnectionAddress());
        assertEquals(energyMeterEntity.getEnergyMeterType().toString(), energyMeterResponsePersistenceDto.getEnergyMeterType());
        assertEquals(energyMeterEntity.getReferenceVoltage(), energyMeterResponsePersistenceDto.getReferenceVoltage());
        assertEquals(energyMeterEntity.getConnectionType().toString(), energyMeterResponsePersistenceDto.getConnectionType());
        assertEquals(energyMeterEntity.getMaxCurrent(), energyMeterResponsePersistenceDto.getMaxCurrent());
        assertEquals(energyMeterEntity.getMidApprovalYear(), energyMeterResponsePersistenceDto.getMidApprovalYear());
        assertEquals(energyMeterEntity.getCreatedAt(), energyMeterResponsePersistenceDto.getCreatedAt());
        assertEquals(energyMeterEntity.getUpdatedAt(), energyMeterResponsePersistenceDto.getUpdatedAt());


    }

    @Test
    public void testEnergyMeterResponseFromPersistenceDtoToDto() {
        EnergyMeterResponsePersistenceDto energyMeterResponsePersistenceDto = new EnergyMeterResponsePersistenceDto(
                1L,
                "CD345323367",
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "U1289",
                400,
                "LON",
                100,
                2021,
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                new ArrayList<CalibrationSchedulePersistenceDto>()
        );

        EnergyMeterResponseDto energyMeterResponseDto = EnergyMeterPersistenceMapper.energyMeterResponseFromPersistenceDtoToDto(energyMeterResponsePersistenceDto);

        assertEquals(energyMeterResponsePersistenceDto.getEnergyMeterId(), energyMeterResponseDto.getEnergyMeterId());
        assertEquals(energyMeterResponsePersistenceDto.getSerialNumber(), energyMeterResponseDto.getSerialNumber());
        assertEquals(energyMeterResponsePersistenceDto.getDeviceType(), energyMeterResponseDto.getDeviceType());
        assertEquals(energyMeterResponsePersistenceDto.getConnectionAddress(), energyMeterResponseDto.getConnectionAddress());
        assertEquals(energyMeterResponsePersistenceDto.getEnergyMeterType(), energyMeterResponseDto.getEnergyMeterType());
        assertEquals(energyMeterResponsePersistenceDto.getReferenceVoltage(), energyMeterResponseDto.getReferenceVoltage());
        assertEquals(energyMeterResponsePersistenceDto.getConnectionType(), energyMeterResponseDto.getConnectionType());
        assertEquals(energyMeterResponsePersistenceDto.getMaxCurrent(), energyMeterResponseDto.getMaxCurrent());
        assertEquals(energyMeterResponsePersistenceDto.getMidApprovalYear(), energyMeterResponseDto.getMidApprovalYear());
        assertEquals(energyMeterResponsePersistenceDto.getCreatedAt(), energyMeterResponseDto.getCreatedAt());
        assertEquals(energyMeterResponsePersistenceDto.getUpdatedAt(), energyMeterResponseDto.getUpdatedAt());
        assertEquals(0, energyMeterResponseDto.getCalibrationSchedules().size());

    }

    @Test
    public void testCreateEnergyMeterRequestFromDtoToPersistenceDto() {
        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "CD345323367",
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "U1289",
                400,
                "LON",
                100,
                2021
        );

        CreateEnergyMeterRequestPersistenceDto createEnergyMeterRequestPersistenceDto = EnergyMeterPersistenceMapper.createEnergyMeterRequestFromDtoToPersistenceDto(createEnergyMeterRequestDto);

        assertEquals(createEnergyMeterRequestDto.getSerialNumber(), createEnergyMeterRequestPersistenceDto.getSerialNumber());
        assertEquals(createEnergyMeterRequestDto.getDeviceType(), createEnergyMeterRequestPersistenceDto.getDeviceType());
        assertEquals(createEnergyMeterRequestDto.getConnectionAddress(), createEnergyMeterRequestPersistenceDto.getConnectionAddress());
        assertEquals(createEnergyMeterRequestDto.getEnergyMeterType(), createEnergyMeterRequestPersistenceDto.getEnergyMeterType());
        assertEquals(createEnergyMeterRequestDto.getReferenceVoltage(), createEnergyMeterRequestPersistenceDto.getReferenceVoltage());
        assertEquals(createEnergyMeterRequestDto.getConnectionType(), createEnergyMeterRequestPersistenceDto.getConnectionType());
        assertEquals(createEnergyMeterRequestDto.getMaxCurrent(), createEnergyMeterRequestPersistenceDto.getMaxCurrent());
        assertEquals(createEnergyMeterRequestDto.getMidApprovalYear(), createEnergyMeterRequestPersistenceDto.getMidApprovalYear());

    }

    private static EnergyMeterEntity getEnergyMeterEntity() {
        EnergyMeterEntity energyMeterEntity = new EnergyMeterEntity();
        energyMeterEntity.setId(1L);
        energyMeterEntity.setSerialNumber("CD345323367");
        energyMeterEntity.setDeviceType(DeviceTypeEntity.ENERGY_METER);
        energyMeterEntity.setDeviceStatus(DeviceStatusEntity.IN_STOCK);
        energyMeterEntity.setConnectionAddress("asdk2323lkjasf");
        energyMeterEntity.setEnergyMeterType(EnergyMeterTypeEntity.DIGITAL);
        energyMeterEntity.setReferenceVoltage(400);
        energyMeterEntity.setConnectionType(ConnectionTypeEntity.LON);
        energyMeterEntity.setMaxCurrent(100);
        energyMeterEntity.setMidApprovalYear(2021);
        energyMeterEntity.setCreatedAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        energyMeterEntity.setUpdatedAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        energyMeterEntity.setCalibrationSchedules(new ArrayList<CalibrationScheduleEntity>());
        return energyMeterEntity;
    }
}
