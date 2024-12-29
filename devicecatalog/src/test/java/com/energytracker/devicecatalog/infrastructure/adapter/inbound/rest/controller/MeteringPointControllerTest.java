package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.config.TestSecurityConfig;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.service.MeteringPointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeteringPointController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class MeteringPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeteringPointService meteringPointService;

    @Autowired
    private ObjectMapper objectMapper;

    MeteringPointResponseDto meteringPointResponseDto;
    EnergyMeterResponseDto energyMeterResponseDto;
    ChannelResponseDto channelResponseDto;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        energyMeterResponseDto = new EnergyMeterResponseDto(
                1L,
                "Serial Number",
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L,
                "Energy Meter 1",
                "installed",
                "connection address",
                "Energy Meter type",
                400,
                "connection type",
                100,
                2024
        );

        channelResponseDto = new ChannelResponseDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L,
                "channel1",
                1,
                4,
                "Channel long description",
                "KWH",
                "KW",
                45,
                1,
                45,
                1,
                true,
                1L
        );


        meteringPointResponseDto = new MeteringPointResponseDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                energyMeterResponseDto,
                channelResponseDto,
                "Channel Location",
                "connection Description",
                true
        );
    }



    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetMeteringPoints() throws Exception {
        when(meteringPointService.getAllMeteringPoints()).thenReturn(Collections.singletonList(meteringPointResponseDto));

        mockMvc.perform(get("/api/v1/metering-points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].meteringPointId").value(1L))
                .andExpect(jsonPath("$[0].locationName").value("Channel Location"))
                .andExpect(jsonPath("$[0].connectionDescription").value("connection Description"))
                .andExpect(jsonPath("$[0].activeStatus").value(true))
                .andExpect(jsonPath("$[0].energyMeter").exists())
                .andExpect(jsonPath("$[0].channel").exists());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetMeteringPointById() throws Exception {
        when(meteringPointService.getMeteringPointById(any(Long.class))).thenReturn(meteringPointResponseDto);

        mockMvc.perform(get("/api/v1/metering-points/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meteringPointId").value(1L))
                .andExpect(jsonPath("$.locationName").value("Channel Location"))
                .andExpect(jsonPath("$.connectionDescription").value("connection Description"))
                .andExpect(jsonPath("$.activeStatus").value(true))
                .andExpect(jsonPath("$.energyMeter").exists())
                .andExpect(jsonPath("$.channel").exists());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testCreateMeteringPoint() throws Exception {
        CreateMeteringPointRequestDto requestDto = new CreateMeteringPointRequestDto();
        when(meteringPointService.createMeteringPoint(any(CreateMeteringPointRequestDto.class))).thenReturn(meteringPointResponseDto);

        mockMvc.perform(post("/api/v1/metering-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meteringPointId").value(1L))
                .andExpect(jsonPath("$.locationName").value("Channel Location"))
                .andExpect(jsonPath("$.connectionDescription").value("connection Description"))
                .andExpect(jsonPath("$.activeStatus").value(true))
                .andExpect(jsonPath("$.energyMeter").exists())
                .andExpect(jsonPath("$.channel").exists());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testUpdateMeteringPoint() throws Exception {
        CreateMeteringPointRequestDto requestDto = new CreateMeteringPointRequestDto();
        requestDto.setActiveStatus(false);
        requestDto.setConnectionDescription("new connection description");
        requestDto.setLocationName("new location name");
        meteringPointResponseDto.setActiveStatus(requestDto.getActiveStatus());
        meteringPointResponseDto.setConnectionDescription(requestDto.getConnectionDescription());
        meteringPointResponseDto.setLocationName(requestDto.getLocationName());
        when(meteringPointService.updateMeteringPointById(any(Long.class), any(CreateMeteringPointRequestDto.class))).thenReturn(meteringPointResponseDto);

        mockMvc.perform(patch("/api/v1/metering-points/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meteringPointId").value(1L))
                .andExpect(jsonPath("$.locationName").value("new location name"))
                .andExpect(jsonPath("$.connectionDescription").value("new connection description"))
                .andExpect(jsonPath("$.activeStatus").value(false));
    }

}
