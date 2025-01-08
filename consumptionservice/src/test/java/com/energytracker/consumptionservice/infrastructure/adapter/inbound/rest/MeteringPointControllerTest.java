package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest;


import com.energytracker.consumptionservice.application.config.TestSecurityConfig;
import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.application.port.inbound.JwtManageUseCase;
import com.energytracker.consumptionservice.application.service.MeteringPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    private JwtManageUseCase jwtManageUseCase;

    MeteringPointDto meteringPointDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        meteringPointDto = new MeteringPointDto(
                "ADD",
                1L,
                2,
                "A3"
        );
    }

    @Test
    public void testGetMeteringPoints_ReturnsOk() throws Exception {
        when(meteringPointService.getAllMeteringPoints()).thenReturn(List.of(meteringPointDto));

        mockMvc.perform(get("/api/v1/metering-points")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meteringPointId").value(1L))
                .andExpect(jsonPath("$[0].channelNumber").value(2))
                .andExpect(jsonPath("$[0].stationTag").value("A3"))
                .andExpect(jsonPath("$[0].actionType").value("ADD"));
    }

    @Test
    public void testGetMeteringPoints_ReturnsNoContent() throws Exception {
        when(meteringPointService.getAllMeteringPoints()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/metering-points")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
