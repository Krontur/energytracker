package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.config.TestSecurityConfig;
import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.service.ChannelService;
import com.energytracker.devicecatalog.application.port.inbound.JwtManageUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChannelController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class ChannelControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelService channelService;

    @MockBean
    private JwtManageUseCase jwtManageUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetChannelById() throws Exception {
        ChannelResponseDto channelResponseDto = new ChannelResponseDto (
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

        when(channelService.getChannelById(any(Long.class))).thenReturn(channelResponseDto);

        mockMvc.perform(get("/api/v1/channels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channelId").value(1))
                .andExpect(jsonPath("$.channelName").value("channel1"))
                .andExpect(jsonPath("$.channelLongName").value("Channel long description"))
                .andExpect(jsonPath("$.energyUnit").value("KWH"))
                .andExpect(jsonPath("$.powerUnit").value("KW"))
                .andExpect(jsonPath("$.uratio").value(45))
                .andExpect(jsonPath("$.iratio").value(1))
                .andExpect(jsonPath("$.pfactor").value(45))
                .andExpect(jsonPath("$.lonSubChannel").value(1))
                .andExpect(jsonPath("$.lonIsActive").value(true))
                .andExpect(jsonPath("$.stationId").value(1));

    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testUpdateChannel() throws Exception {
        ChannelResponseDto channelResponseDto = new ChannelResponseDto (
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

        when(channelService.updateChannel(any(ChannelResponseDto.class))).thenReturn(channelResponseDto);

        mockMvc.perform(post("/api/v1/channels")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(channelResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channelId").value(1))
                .andExpect(jsonPath("$.channelName").value("channel1"))
                .andExpect(jsonPath("$.channelLongName").value("Channel long description"))
                .andExpect(jsonPath("$.energyUnit").value("KWH"))
                .andExpect(jsonPath("$.powerUnit").value("KW"))
                .andExpect(jsonPath("$.uratio").value(45))
                .andExpect(jsonPath("$.iratio").value(1))
                .andExpect(jsonPath("$.pfactor").value(45))
                .andExpect(jsonPath("$.lonSubChannel").value(1))
                .andExpect(jsonPath("$.lonIsActive").value(true))
                .andExpect(jsonPath("$.stationId").value(1));
    }
}
