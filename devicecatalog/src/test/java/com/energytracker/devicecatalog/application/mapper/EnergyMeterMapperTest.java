package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.domain.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyMeterMapperTest {

    @Test
    public void testCreateEnergyMeterRequestDomainToDto() throws Exception{

        EnergyMeter energyMeter = new EnergyMeter(
                "JI45345345",
                DeviceType.ENERGY_METER,
                DeviceStatus.IN_STOCK,
                "asdoina98hasdonasd",
                EnergyMeterType.DIGITAL,
                400,
                ConnectionType.LON,
                50,
                2021
        );

        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "JI45345345",
                "ENERGY_METER",
                "IN_STOCK",
                "asdoina98hasdonasd",
                "DIGITAL",
                400,
                "LON",
                50,
                2021
        );

        CreateEnergyMeterRequestDto result = EnergyMeterMapper.createEnergyMeterRequestDomainToDto(energyMeter);

        assertEquals(createEnergyMeterRequestDto.getSerialNumber(), result.getSerialNumber());
        assertEquals(createEnergyMeterRequestDto.getDeviceType(), result.getDeviceType());
        assertEquals(createEnergyMeterRequestDto.getDeviceStatus(), result.getDeviceStatus());
        assertEquals(createEnergyMeterRequestDto.getConnectionAddress(), result.getConnectionAddress());
        assertEquals(createEnergyMeterRequestDto.getEnergyMeterType(), result.getEnergyMeterType());
        assertEquals(createEnergyMeterRequestDto.getReferenceVoltage(), result.getReferenceVoltage());
        assertEquals(createEnergyMeterRequestDto.getConnectionType(), result.getConnectionType());
        assertEquals(createEnergyMeterRequestDto.getMaxCurrent(), result.getMaxCurrent());
        assertEquals(createEnergyMeterRequestDto.getMidApprovalYear(), result.getMidApprovalYear());



    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateEnergyMeterRequestDtoToDomain() throws Exception{

        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "JI45345345",
                "ENERGY_METER",
                "IN_STOCK",
                "asdoina98hasdonasd",
                "DIGITAL",
                400,
                "LON",
                50,
                2021
        );

        EnergyMeter energyMeter = new EnergyMeter(
                "JI45345345",
                DeviceType.ENERGY_METER,
                DeviceStatus.IN_STOCK,
                "asdoina98hasdonasd",
                EnergyMeterType.DIGITAL,
                400,
                ConnectionType.LON,
                50,
                2021
        );

        EnergyMeter result = EnergyMeterMapper.createEnergyMeterRequestDtoToDomain(createEnergyMeterRequestDto);

        assertEquals(energyMeter.getSerialNumber(), result.getSerialNumber());
        assertEquals(energyMeter.getDeviceType(), result.getDeviceType());
        assertEquals(energyMeter.getDeviceStatus(), result.getDeviceStatus());
        assertEquals(energyMeter.getConnectionAddress(), result.getConnectionAddress());
        assertEquals(energyMeter.getEnergyMeterType(), result.getEnergyMeterType());
        assertEquals(energyMeter.getReferenceVoltage(), result.getReferenceVoltage());
        assertEquals(energyMeter.getConnectionType(), result.getConnectionType());
        assertEquals(energyMeter.getMaxCurrent(), result.getMaxCurrent());
        assertEquals(energyMeter.getMidApprovalYear(), result.getMidApprovalYear());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEnergyMeterDomainToResponseDto() throws Exception{

            EnergyMeter energyMeter = new EnergyMeter(
                    1L,
                    "JI45345345",
                    DeviceType.ENERGY_METER,
                    DeviceStatus.IN_STOCK,
                    "asdoina98hasdonasd",
                    EnergyMeterType.DIGITAL,
                    400,
                    ConnectionType.LON,
                    50,
                    2021,
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 1, 0, 0)
            );

            EnergyMeterResponseDto energyMeterResponseDto = new EnergyMeterResponseDto(
                    1L,
                    "JI45345345",
                    "ENERGY_METER",
                    "IN_STOCK",
                    "asdoina98hasdonasd",
                    "DIGITAL",
                    400,
                    "LON",
                    50,
                    2021,
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 1, 0, 0)
            );

            EnergyMeterResponseDto result = EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter);

            assertEquals(energyMeterResponseDto.getSerialNumber(), result.getSerialNumber());
            assertEquals(energyMeterResponseDto.getDeviceType(), result.getDeviceType());
            assertEquals(energyMeterResponseDto.getDeviceStatus(), result.getDeviceStatus());
            assertEquals(energyMeterResponseDto.getConnectionAddress(), result.getConnectionAddress());
            assertEquals(energyMeterResponseDto.getEnergyMeterType(), result.getEnergyMeterType());
            assertEquals(energyMeterResponseDto.getReferenceVoltage(), result.getReferenceVoltage());
            assertEquals(energyMeterResponseDto.getConnectionType(), result.getConnectionType());
            assertEquals(energyMeterResponseDto.getMaxCurrent(), result.getMaxCurrent());
            assertEquals(energyMeterResponseDto.getMidApprovalYear(), result.getMidApprovalYear());
    }

}
