package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnergyMeterServiceTest {

    @Mock
    private EnergyMeterRepositoryPort energyMeterRepositoryPort;

    @InjectMocks
    private EnergyMeterService energyMeterService;

    @Test
    public void testCreateEnergyMeter() {
        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "CD345323367",
                "ENERGY_METER",
                "INSTALLED",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021
        );

        EnergyMeter energyMeter = new EnergyMeter(
                1L,
                "CD345323367",
                LocalDateTime.of( 2021, 1, 1, 0, 0),
                LocalDateTime.of( 2021, 1, 1, 0, 0),
                0L,
                DeviceType.valueOf("ENERGY_METER"),
                DeviceStatus.valueOf("INSTALLED"),
                "asdk2323lkjasf",
                EnergyMeterType.valueOf("DIGITAL"),
                400,
                ConnectionType.valueOf("LON"),
                100,
                2021,
                new ArrayList<CalibrationSchedule>()
        );

        when(energyMeterRepositoryPort.createEnergyMeter(any(EnergyMeter.class))).thenReturn(energyMeter);

        EnergyMeterResponseDto result = energyMeterService.createEnergyMeter(createEnergyMeterRequestDto);

        assertNotNull(result);
        assertEquals(createEnergyMeterRequestDto.getSerialNumber(), result.getSerialNumber());
        assertEquals(createEnergyMeterRequestDto.getDeviceType(), result.getDeviceType());
        assertEquals(createEnergyMeterRequestDto.getConnectionAddress(), result.getConnectionAddress());
        assertEquals(createEnergyMeterRequestDto.getEnergyMeterType(), result.getEnergyMeterType());
        assertEquals(createEnergyMeterRequestDto.getReferenceVoltage(), result.getReferenceVoltage());
        assertEquals(createEnergyMeterRequestDto.getConnectionType(), result.getConnectionType());
        assertEquals(createEnergyMeterRequestDto.getMaxCurrent(), result.getMaxCurrent());
        assertEquals(createEnergyMeterRequestDto.getMidApprovalYear(), result.getMidApprovalYear());
        assertEquals(energyMeter.getCreatedAt(), result.getCreatedAt());
        assertEquals(energyMeter.getUpdatedAt(), result.getUpdatedAt());
    }

}
