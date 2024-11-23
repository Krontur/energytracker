package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.config.TestSecurityConfig;
import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.service.StationService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(StationController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationService stationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetStationById() throws Exception{

        List<ChannelResponseDto> channelResponseDtoList = new ArrayList<ChannelResponseDto>();

        StationResponseDto stationResponseDto = new StationResponseDto(
                1L,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                "HU234234234",
                "STATION",
                "IN_STOCK",
                "Energy Station 1",
                "U1604",
                90,
                "A1",
                channelResponseDtoList
        );

        when(stationService.getStationById(1L)).thenReturn(stationResponseDto);

        mockMvc.perform(get("/api/v1/stations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationId").value(stationResponseDto.getStationId()))
                .andExpect(jsonPath("$.serialNumber").value(stationResponseDto.getSerialNumber()))
                .andExpect(jsonPath("$.stationName").value(stationResponseDto.getStationName()))
                .andExpect(jsonPath("$.deviceType").value(stationResponseDto.getDeviceType()))
                .andExpect(jsonPath("$.deviceStatus").value(stationResponseDto.getDeviceStatus()))
                .andExpect(jsonPath("$.stationType").value(stationResponseDto.getStationType()))
                .andExpect(jsonPath("$.readingIntervalInSeconds").value(stationResponseDto.getReadingIntervalInSeconds()))
                .andExpect(jsonPath("$.channelList", hasSize(0)));

    }


}
