package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.application.mapper.MeteringPointMapper;
import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.ActionType;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MeteringPointServiceTest {

    @Mock
    private MeteringPointRepositoryPort meteringPointRepositoryPort;

    @MockBean
    private MeteringPointService meteringPointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meteringPointService = new MeteringPointService(meteringPointRepositoryPort);
    }

    @Test
    void testGetAllMeteringPoints_whenMeteringPointsExist() {
        List<MeteringPoint> meteringPoints = new ArrayList<>();
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setMeteringPointId(1L);
        meteringPoint.setActionType(ActionType.ADD);
        meteringPoint.setStationTag("A2");
        meteringPoint.setChannelNumber(34);
        meteringPoints.add(meteringPoint);


        when(meteringPointRepositoryPort.getAllMeteringPoints())
                .thenReturn(meteringPoints);

        List<MeteringPointDto> result = meteringPointService.getAllMeteringPoints();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A2", result.get(0).getStationTag());
        verify(meteringPointRepositoryPort, times(1)).getAllMeteringPoints();
    }

    @Test
    void testGetAllMeteringPoints_whenNoMeteringPointsExist() {
        when(meteringPointRepositoryPort.getAllMeteringPoints()).thenReturn(Collections.emptyList());

        List<MeteringPointDto> result = meteringPointService.getAllMeteringPoints();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(meteringPointRepositoryPort, times(1)).getAllMeteringPoints();
    }

    @Test
    void testGetAllMeteringPoints_whenRepositoryReturnsNull() {
        when(meteringPointRepositoryPort.getAllMeteringPoints()).thenReturn(null);

        List<MeteringPointDto> result = meteringPointService.getAllMeteringPoints();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(meteringPointRepositoryPort, times(1)).getAllMeteringPoints();
    }
}
