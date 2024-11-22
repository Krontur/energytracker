package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.domain.model.*;
import org.junit.jupiter.api.Test;

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

}
