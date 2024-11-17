package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateEnergyMeterRequestRestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyMapperRestMapperTest {

    @Test
    public void testCreateEnergyMeterRequestFromRestDtoToDto() {
        CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto = new CreateEnergyMeterRequestRestDto(
                "FD234123654",
                "ENERGY_METER",
                "908asd89hasd78",
                "U2289",
                400,
                "LON",
                85,
                2022
        );

        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = EnergyMeterRestMapper.createEnergyMeterRequestFromRestDtoToDto(createEnergyMeterRequestRestDto);

        assertEquals(createEnergyMeterRequestRestDto.getSerialNumber(), createEnergyMeterRequestDto.getSerialNumber());
        assertEquals(createEnergyMeterRequestRestDto.getDeviceType(), createEnergyMeterRequestDto.getDeviceType());
        assertEquals(createEnergyMeterRequestRestDto.getConnectionAddress(), createEnergyMeterRequestDto.getConnectionAddress());
        assertEquals(createEnergyMeterRequestRestDto.getEnergyMeterType(), createEnergyMeterRequestDto.getEnergyMeterType());
        assertEquals(createEnergyMeterRequestRestDto.getReferenceVoltage(), createEnergyMeterRequestDto.getReferenceVoltage());
        assertEquals(createEnergyMeterRequestRestDto.getConnectionType(), createEnergyMeterRequestDto.getConnectionType());
        assertEquals(createEnergyMeterRequestRestDto.getMaxCurrent(), createEnergyMeterRequestDto.getMaxCurrent());
        assertEquals(createEnergyMeterRequestRestDto.getMidApprovalYear(), createEnergyMeterRequestDto.getMidApprovalYear());

    }

}
