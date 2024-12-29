package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest;

import com.energytracker.consumptionservice.application.config.TestSecurityConfig;
import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.service.ConsumptionService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ConsumptionController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class ConsumptionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsumptionService consumptionService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<ConsumptionDto> consumptions;
    private ConsumptionQueryDto consumptionQueryDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ConsumptionDto consumptionDto1 = new ConsumptionDto(1L, 15.0, LocalDateTime.now());
        ConsumptionDto consumptionDto2 = new ConsumptionDto(2L, 20.0, LocalDateTime.now().plusMinutes(15));
        consumptions = Arrays.asList(consumptionDto1, consumptionDto2);

        consumptionQueryDto = new ConsumptionQueryDto();
        consumptionQueryDto.setMeteringPointId(1L);
        consumptionQueryDto.setStartDateTime(LocalDateTime.now());
        consumptionQueryDto.setEndDateTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetConsumptionsByMeteringPointId_WhenDataExists() throws Exception {
        when(consumptionService.getConsumptionsByMeteringPointId(1L)).thenReturn(consumptions);

        mockMvc.perform(get("/api/v1/consumptions/metering-point/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meteringPointId").value(1L))
                .andExpect(jsonPath("$[0].consumptionValue").value(15.0))
                .andExpect(jsonPath("$[1].meteringPointId").value(2L))
                .andExpect(jsonPath("$[1].consumptionValue").value(20.0));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetConsumptionsByMeteringPointId_WhenNoData() throws Exception {
        when(consumptionService.getConsumptionsByMeteringPointId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/consumptions/metering-point/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetConsumptionsByMeteringPointIdAndInterval_WhenDataExists() throws Exception {
        when(consumptionService.getConsumptionsByMeteringPointIdAndInterval(any(ConsumptionQueryDto.class)))
                .thenReturn(consumptions);

        mockMvc.perform(post("/api/v1/consumptions/metering-point/interval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consumptionQueryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meteringPointId").value(1L))
                .andExpect(jsonPath("$[0].consumptionValue").value(15.0))
                .andExpect(jsonPath("$[1].meteringPointId").value(2L))
                .andExpect(jsonPath("$[1].consumptionValue").value(20.0));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testGetConsumptionsByMeteringPointIdAndInterval_WhenNoData() throws Exception {
        when(consumptionService.getConsumptionsByMeteringPointIdAndInterval(any(ConsumptionQueryDto.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/v1/consumptions/metering-point/interval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consumptionQueryDto)))
                .andExpect(status().isNoContent());
    }

}
