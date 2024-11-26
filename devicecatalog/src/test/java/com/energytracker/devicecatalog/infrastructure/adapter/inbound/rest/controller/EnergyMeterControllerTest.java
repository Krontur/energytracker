package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.energytracker.devicecatalog.application.service.EnergyMeterService;
import com.energytracker.devicecatalog.application.config.TestSecurityConfig;

@WebMvcTest(EnergyMeterController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class EnergyMeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnergyMeterService energyMeterService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testCreateEnergyMeter() throws Exception {
        CreateEnergyMeterRequestDto createEnergyMeterRequestDto = new CreateEnergyMeterRequestDto(
                "CD345323367",
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021
        );

        EnergyMeterResponseDto energyMeterResponseDto = new EnergyMeterResponseDto(
                1L,
                createEnergyMeterRequestDto.getSerialNumber(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getDeviceStatus(),
                createEnergyMeterRequestDto.getConnectionAddress(),
                createEnergyMeterRequestDto.getEnergyMeterType(),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                createEnergyMeterRequestDto.getConnectionType(),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear(),
                new ArrayList<CalibrationScheduleResponseDto>()

        );

        when(energyMeterService.createEnergyMeter(any())).thenReturn(energyMeterResponseDto);

        mockMvc.perform(post("/api/v1/meters")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(energyMeterResponseDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.serialNumber").value(createEnergyMeterRequestDto.getSerialNumber()))
                        .andExpect(jsonPath("$.deviceType").value(createEnergyMeterRequestDto.getDeviceType()))
                        .andExpect(jsonPath("$.connectionAddress").value(createEnergyMeterRequestDto.getConnectionAddress()))
                        .andExpect(jsonPath("$.energyMeterType").value(createEnergyMeterRequestDto.getEnergyMeterType()))
                        .andExpect(jsonPath("$.referenceVoltage").value(createEnergyMeterRequestDto.getReferenceVoltage()))
                        .andExpect(jsonPath("$.connectionType").value(createEnergyMeterRequestDto.getConnectionType()))
                        .andExpect(jsonPath("$.maxCurrent").value(createEnergyMeterRequestDto.getMaxCurrent()))
                        .andExpect(jsonPath("$.midApprovalYear").value(createEnergyMeterRequestDto.getMidApprovalYear())
        );
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetMeterById () throws Exception {

        EnergyMeterResponseDto energyMeterResponseDto = new EnergyMeterResponseDto(
                1L,
                "CD345323367",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<CalibrationScheduleResponseDto>()
        );

        when(energyMeterService.getEnergyMeterById(1L)).thenReturn(energyMeterResponseDto);

        mockMvc.perform(get("/api/v1/meters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(energyMeterResponseDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.serialNumber").value("CD345323367"))
                        .andExpect(jsonPath("$.deviceType").value("ENERGY_METER"))
                        .andExpect(jsonPath("$.connectionAddress").value("asdk2323lkjasf"))
                        .andExpect(jsonPath("$.energyMeterType").value("DIGITAL"))
                        .andExpect(jsonPath("$.referenceVoltage").value(400))
                        .andExpect(jsonPath("$.connectionType").value("LON"))
                        .andExpect(jsonPath("$.maxCurrent").value(100))
                        .andExpect(jsonPath("$.midApprovalYear").value(2021)
        );

    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetAllMeters() throws Exception {

        List<EnergyMeterResponseDto> energyMeterResponseDtoList = new ArrayList<>();
        energyMeterResponseDtoList.add( new EnergyMeterResponseDto(
                1L,
                "CD345323367",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<CalibrationScheduleResponseDto>()
        ));

        energyMeterResponseDtoList.add(
                new EnergyMeterResponseDto(
                2L,
                "CD345323368",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<CalibrationScheduleResponseDto>()
        ));

        when(energyMeterService.getAllEnergyMeters()).thenReturn(energyMeterResponseDtoList);

        mockMvc.perform(get("/api/v1/meters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(energyMeterResponseDtoList)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.*").isArray())
                        .andExpect(jsonPath("$.*").isNotEmpty())
                        .andExpect(jsonPath("$.length()").value(2))
                        .andExpect(jsonPath("$[0].serialNumber").value("CD345323367"))
                        .andExpect(jsonPath("$[0].deviceType").value("ENERGY_METER"))
                        .andExpect(jsonPath("$[0].connectionAddress").value("asdk2323lkjasf"))
                        .andExpect(jsonPath("$[0].energyMeterType").value("DIGITAL"))
                        .andExpect(jsonPath("$[0].referenceVoltage").value(400))
                        .andExpect(jsonPath("$[0].connectionType").value("LON"))
                        .andExpect(jsonPath("$[0].maxCurrent").value(100))
                        .andExpect(jsonPath("$[0].midApprovalYear").value(2021))
                        .andExpect(jsonPath("$[1].serialNumber").value("CD345323368"))
                        .andExpect(jsonPath("$[1].deviceType").value("ENERGY_METER"))
                        .andExpect(jsonPath("$[1].connectionAddress").value("asdk2323lkjasf"))
                        .andExpect(jsonPath("$[1].energyMeterType").value("DIGITAL"))
                        .andExpect(jsonPath("$[1].referenceVoltage").value(400))
                        .andExpect(jsonPath("$[1].connectionType").value("LON"))
                        .andExpect(jsonPath("$[1].maxCurrent").value(100))
                        .andExpect(jsonPath("$[1].midApprovalYear").value(2021)
        );
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testDeactivateEnergyMeterById() throws Exception {
        EnergyMeterRequestDto energyMeterRequestDto = new EnergyMeterRequestDto(
                1L,
                "CD345323367",
                "ENERGY_METER",
                "IN_STOCK",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<CalibrationScheduleRequestDto>()
        );

        EnergyMeterResponseDto energyMeterResponseDto = new EnergyMeterResponseDto(
                1L,
                "CD345323367",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                "ENERGY_METER",
                "DEACTIVATED",
                "asdk2323lkjasf",
                "DIGITAL",
                400,
                "LON",
                100,
                2021,
                new ArrayList<CalibrationScheduleResponseDto>()
        );

        when(energyMeterService.deactivateEnergyMeterById(1L)).thenReturn(energyMeterResponseDto);

        mockMvc.perform(post("/api/v1/meters/1/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(energyMeterResponseDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.serialNumber").value("CD345323367"))
                        .andExpect(jsonPath("$.deviceType").value("ENERGY_METER"))
                        .andExpect(jsonPath("$.deviceStatus").value("DEACTIVATED"))
                        .andExpect(jsonPath("$.connectionAddress").value("asdk2323lkjasf"))
                        .andExpect(jsonPath("$.energyMeterType").value("DIGITAL"))
                        .andExpect(jsonPath("$.referenceVoltage").value(400))
                        .andExpect(jsonPath("$.connectionType").value("LON"))
                        .andExpect(jsonPath("$.maxCurrent").value(100))
                        .andExpect(jsonPath("$.midApprovalYear").value(2021)
        );
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testDeleteEnergyMeterById() throws Exception {
        mockMvc.perform(delete("/api/v1/meters/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

}
