package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsumptionServiceTest {

    @Mock
    private ConsumptionRepositoryPort consumptionRepositoryPort;

    private ConsumptionService consumptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumptionService = new ConsumptionService(consumptionRepositoryPort);
    }

    @Test
    void testGetConsumptionsByMeteringPointIdAndInterval_validInterval() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        when(consumptionRepositoryPort.findConsumptionsByMeteringPointIdAndInterval(any(), any(), any()))
                .thenReturn(Collections.singletonList(consumption));

        ConsumptionQueryDto queryDto = new ConsumptionQueryDto(1L, "INTERVAL", LocalDateTime.now(), LocalDateTime.now());
        List<ConsumptionDto> result = consumptionService.getConsumptionsByMeteringPointIdAndInterval(queryDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consumptionRepositoryPort, times(1)).findConsumptionsByMeteringPointIdAndInterval(any(), any(), any());
    }

    @Test
    void testGetConsumptionsByMeteringPointIdAndInterval_invalidIntervalType() {
        ConsumptionQueryDto queryDto = new ConsumptionQueryDto(1L, "INVALID", LocalDateTime.now(), LocalDateTime.now());
        List<ConsumptionDto> result = consumptionService.getConsumptionsByMeteringPointIdAndInterval(queryDto);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verifyNoInteractions(consumptionRepositoryPort);
    }

    @Test
    void testReceiveMessage_validConsumptions() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        List<Consumption> consumptions = Collections.singletonList(consumption);

        consumptionService.receiveMessage(consumptions);

        verify(consumptionRepositoryPort, times(1)).saveAllConsumptions(consumptions);
    }

    @Test
    void testReceiveMessage_nullConsumptions() {
        consumptionService.receiveMessage(null);

        verifyNoInteractions(consumptionRepositoryPort);
    }

    @Test
    void testGetDailyConsumptionsByMeteringPointIdAndInterval() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        when(consumptionRepositoryPort.findDailyConsumptionsByMeteringPointId(any(), any(), any()))
                .thenReturn(Collections.singletonList(consumption));

        ConsumptionQueryDto queryDto = new ConsumptionQueryDto(1L, "DAILY", LocalDateTime.now(), LocalDateTime.now());
        List<ConsumptionDto> result = consumptionService.getDailyConsumptionsByMeteringPointIdAndInterval(queryDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consumptionRepositoryPort, times(1)).findDailyConsumptionsByMeteringPointId(any(), any(), any());
    }

    @Test
    void testGetMonthlyConsumptionsByMeteringPointIdAndInterval() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        when(consumptionRepositoryPort.findMonthlyConsumptionsByMeteringPointId(any(), any(), any()))
                .thenReturn(Collections.singletonList(consumption));

        ConsumptionQueryDto queryDto = new ConsumptionQueryDto(1L, "MONTHLY", LocalDateTime.now(), LocalDateTime.now());
        List<ConsumptionDto> result = consumptionService.getMonthlyConsumptionsByMeteringPointIdAndInterval(queryDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consumptionRepositoryPort, times(1)).findMonthlyConsumptionsByMeteringPointId(any(), any(), any());
    }

    @Test
    void testGetYearlyConsumptionsByMeteringPointIdAndInterval() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        when(consumptionRepositoryPort.findYearlyConsumptionsByMeteringPointId(any(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(consumption));

        ConsumptionQueryDto queryDto = new ConsumptionQueryDto(1L, "YEARLY", LocalDateTime.now(), LocalDateTime.now());
        List<ConsumptionDto> result = consumptionService.getYearlyConsumptionsByMeteringPointIdAndInterval(queryDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consumptionRepositoryPort, times(1)).findYearlyConsumptionsByMeteringPointId(any(), anyInt(), anyInt());
    }

    @Test
    void testGetConsumptionsByMeteringPointId() {
        Consumption consumption = new Consumption(1L, 100.0, LocalDateTime.now());
        when(consumptionRepositoryPort.findConsumptionsByMeteringPointId(anyLong()))
                .thenReturn(Collections.singletonList(consumption));

        List<ConsumptionDto> result = consumptionService.getConsumptionsByMeteringPointId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consumptionRepositoryPort, times(1)).findConsumptionsByMeteringPointId(anyLong());
    }
}
