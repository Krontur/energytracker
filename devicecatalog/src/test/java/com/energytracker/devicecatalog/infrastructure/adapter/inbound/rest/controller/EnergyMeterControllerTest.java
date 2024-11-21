package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.energytracker.devicecatalog.application.service.EnergyMeterService;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateEnergyMeterRequestRestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;


@WebMvcTest(EnergyMeterController.class)
@ActiveProfiles("test")
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
        CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto = new CreateEnergyMeterRequestRestDto(
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
                createEnergyMeterRequestRestDto.getSerialNumber(),
                createEnergyMeterRequestRestDto.getDeviceType(),
                createEnergyMeterRequestRestDto.getDeviceStatus(),
                createEnergyMeterRequestRestDto.getConnectionAddress(),
                createEnergyMeterRequestRestDto.getEnergyMeterType(),
                createEnergyMeterRequestRestDto.getReferenceVoltage(),
                createEnergyMeterRequestRestDto.getConnectionType(),
                createEnergyMeterRequestRestDto.getMaxCurrent(),
                createEnergyMeterRequestRestDto.getMidApprovalYear(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<CalibrationScheduleResponseDto>()

        );

        when(energyMeterService.createEnergyMeter(any())).thenReturn(energyMeterResponseDto);

        mockMvc.perform(post("/api/v1/meters")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(energyMeterResponseDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.serialNumber").value(createEnergyMeterRequestRestDto.getSerialNumber()))
                        .andExpect(jsonPath("$.deviceType").value(createEnergyMeterRequestRestDto.getDeviceType()))
                        .andExpect(jsonPath("$.connectionAddress").value(createEnergyMeterRequestRestDto.getConnectionAddress()))
                        .andExpect(jsonPath("$.energyMeterType").value(createEnergyMeterRequestRestDto.getEnergyMeterType()))
                        .andExpect(jsonPath("$.referenceVoltage").value(createEnergyMeterRequestRestDto.getReferenceVoltage()))
                        .andExpect(jsonPath("$.connectionType").value(createEnergyMeterRequestRestDto.getConnectionType()))
                        .andExpect(jsonPath("$.maxCurrent").value(createEnergyMeterRequestRestDto.getMaxCurrent()))
                        .andExpect(jsonPath("$.midApprovalYear").value(createEnergyMeterRequestRestDto.getMidApprovalYear())
        );
    }

}
