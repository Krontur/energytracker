package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.config.TestSecurityConfig;
import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.JwtManageUseCase;
import com.energytracker.devicecatalog.application.service.StationService;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @MockBean
    private JwtManageUseCase jwtManageUseCase;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateStation() throws Exception {

        CreateStationRequestDto createStationRequestDto = new CreateStationRequestDto(
                "HU234234234",
                "STATION",
                "IN_STOCK",
                "Energy Station 1",
                "U1604",
                "A1",
                90
        );

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

        when(stationService.createStation(any())).thenReturn(stationResponseDto);

        mockMvc.perform(post("/api/v1/stations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStationRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serialNumber").value(stationResponseDto.getSerialNumber()))
                .andExpect(jsonPath("$.stationName").value(stationResponseDto.getStationName()))
                .andExpect(jsonPath("$.deviceType").value(stationResponseDto.getDeviceType()))
                .andExpect(jsonPath("$.deviceStatus").value(stationResponseDto.getDeviceStatus()))
                .andExpect(jsonPath("$.stationType").value(stationResponseDto.getStationType()))
                .andExpect(jsonPath("$.readingIntervalInSeconds").value(stationResponseDto.getReadingIntervalInSeconds()))
                .andExpect(jsonPath("$.channelList", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllStations() throws Exception {

        List<ChannelResponseDto> channelResponseDtoList = new ArrayList<ChannelResponseDto>();

        List<StationResponseDto> stationResponseDtoList = new ArrayList<StationResponseDto>();
        stationResponseDtoList.add(new StationResponseDto(
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
        ));

        when(stationService.getAllStations()).thenReturn(stationResponseDtoList);

        mockMvc.perform(get("/api/v1/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].stationId").value(stationResponseDtoList.get(0).getStationId()))
                .andExpect(jsonPath("$[0].serialNumber").value(stationResponseDtoList.get(0).getSerialNumber()))
                .andExpect(jsonPath("$[0].stationName").value(stationResponseDtoList.get(0).getStationName()))
                .andExpect(jsonPath("$[0].deviceType").value(stationResponseDtoList.get(0).getDeviceType()))
                .andExpect(jsonPath("$[0].deviceStatus").value(stationResponseDtoList.get(0).getDeviceStatus()))
                .andExpect(jsonPath("$[0].stationType").value(stationResponseDtoList.get(0).getStationType()))
                .andExpect(jsonPath("$[0].readingIntervalInSeconds").value(stationResponseDtoList.get(0).getReadingIntervalInSeconds()))
                .andExpect(jsonPath("$[0].channelList", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteStationById() throws Exception {

        mockMvc.perform(delete("/api/v1/stations/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeactivateStationById() throws Exception {

        List<ChannelResponseDto> channelResponseDtoList = new ArrayList<ChannelResponseDto>();

        StationResponseDto stationResponseDto = new StationResponseDto(
                1L,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                "HU234234234",
                "STATION",
                "DEACTIVATED",
                "Energy Station 1",
                "U1604",
                90,
                "A1",
                channelResponseDtoList
        );

        when(stationService.deactivateStationById(1L)).thenReturn(stationResponseDto);

        mockMvc.perform(post("/api/v1/stations/1/deactivate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationId").value(stationResponseDto.getStationId()))
                .andExpect(jsonPath("$.serialNumber").value(stationResponseDto.getSerialNumber()))
                .andExpect(jsonPath("$.stationName").value(stationResponseDto.getStationName()))
                .andExpect(jsonPath("$.deviceType").value(stationResponseDto.getDeviceType()))
                .andExpect(jsonPath("$.deviceStatus").value("DEACTIVATED"))
                .andExpect(jsonPath("$.stationType").value(stationResponseDto.getStationType()))
                .andExpect(jsonPath("$.readingIntervalInSeconds").value(stationResponseDto.getReadingIntervalInSeconds()))
                .andExpect(jsonPath("$.channelList", hasSize(0)));

    }


}
