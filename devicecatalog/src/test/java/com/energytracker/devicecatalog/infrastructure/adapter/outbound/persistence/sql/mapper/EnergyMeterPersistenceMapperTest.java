package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyMeterPersistenceMapperTest {

    @Test
    public void testEnergyMeterEntityToResponseDto(){
        EnergyMeterEntity energyMeterEntity = getEnergyMeterEntity();

        EnergyMeterResponseDto energyMeterResponseDto = EnergyMeterPersistenceMapper.energyMeterResponseEntityToDto(energyMeterEntity);

        assertEquals(energyMeterEntity.getId(), energyMeterResponseDto.getEnergyMeterId());
        assertEquals(energyMeterEntity.getSerialNumber(), energyMeterResponseDto.getSerialNumber());
        assertEquals(energyMeterEntity.getDeviceType().toString(), energyMeterResponseDto.getDeviceType());
        assertEquals(energyMeterEntity.getConnectionAddress(), energyMeterResponseDto.getConnectionAddress());
        assertEquals(energyMeterEntity.getEnergyMeterType().toString(), energyMeterResponseDto.getEnergyMeterType());
        assertEquals(energyMeterEntity.getReferenceVoltage(), energyMeterResponseDto.getReferenceVoltage());
        assertEquals(energyMeterEntity.getConnectionType().toString(), energyMeterResponseDto.getConnectionType());
        assertEquals(energyMeterEntity.getMaxCurrent(), energyMeterResponseDto.getMaxCurrent());
        assertEquals(energyMeterEntity.getMidApprovalYear(), energyMeterResponseDto.getMidApprovalYear());
        assertEquals(energyMeterEntity.getCreatedAt(), energyMeterResponseDto.getCreatedAt());
        assertEquals(energyMeterEntity.getUpdatedAt(), energyMeterResponseDto.getUpdatedAt());
    }

    @Test
    public void testCreateEnergyMeterRequestDtoToEntity(){
        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "CD345323367",
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<>()
        );

        createEnergyMeterRequestDto.getCalibrationSchedules().add(new CalibrationScheduleRequestDto(
                LocalDate.of(2029,1,1),
                LocalDate.of(2021,1,1),
                8,
                "First Calibration",
                "PENDING"
        ));

        EnergyMeterEntity energyMeterEntity = EnergyMeterPersistenceMapper.createEnergyMeterRequestDtoToEntity(createEnergyMeterRequestDto);

        assertEquals(createEnergyMeterRequestDto.getSerialNumber(), energyMeterEntity.getSerialNumber());
        assertEquals(createEnergyMeterRequestDto.getDeviceType(), energyMeterEntity.getDeviceType().toString());
        assertEquals(createEnergyMeterRequestDto.getConnectionAddress(), energyMeterEntity.getConnectionAddress());
        assertEquals(createEnergyMeterRequestDto.getEnergyMeterType(), energyMeterEntity.getEnergyMeterType().toString());
        assertEquals(createEnergyMeterRequestDto.getReferenceVoltage(), energyMeterEntity.getReferenceVoltage());
        assertEquals(createEnergyMeterRequestDto.getConnectionType(), energyMeterEntity.getConnectionType().toString());
        assertEquals(createEnergyMeterRequestDto.getMaxCurrent(), energyMeterEntity.getMaxCurrent());
        assertEquals(createEnergyMeterRequestDto.getMidApprovalYear(), energyMeterEntity.getMidApprovalYear());
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
