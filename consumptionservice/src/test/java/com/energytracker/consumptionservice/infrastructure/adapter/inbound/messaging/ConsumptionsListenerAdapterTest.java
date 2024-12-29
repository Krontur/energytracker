package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.ConsumptionMessageHandlerPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsumptionsListenerAdapterTest {

    @Mock
    private ConsumptionMessageHandlerPort consumptionMessageHandlerPort;

    @InjectMocks
    private ConsumptionsListenerAdapter consumptionsListenerAdapter;

    @Test
    void testReceiveMessage() {
        MockitoAnnotations.openMocks(this);
        Consumption consumption1 = new Consumption(1L, 10L, 15.5, LocalDateTime.of(2024, 12, 29, 10, 0));
        Consumption consumption2 = new Consumption(2L, 11L, 20.0, LocalDateTime.of(2024, 12, 29, 10, 15));
        List<Consumption> consumptions = Arrays.asList(consumption1, consumption2);

        consumptionsListenerAdapter.receiveMessage(consumptions);

        ArgumentCaptor<List<Consumption>> captor = ArgumentCaptor.forClass(List.class);
        verify(consumptionMessageHandlerPort, times(1)).receiveMessage(captor.capture());
        List<Consumption> capturedConsumptions = captor.getValue();

        assertEquals(2, capturedConsumptions.size());
        assertEquals(consumption1, capturedConsumptions.get(0));
        assertEquals(consumption2, capturedConsumptions.get(1));
    }

    @Test
    void testReceiveMessageWithEmptyList() {
        MockitoAnnotations.openMocks(this);
        List<Consumption> emptyConsumptions = List.of();

        consumptionsListenerAdapter.receiveMessage(emptyConsumptions);

        verify(consumptionMessageHandlerPort, times(1)).receiveMessage(emptyConsumptions);
    }

    @Test
    void testReceiveMessageWithNull() {
        MockitoAnnotations.openMocks(this);

        consumptionsListenerAdapter.receiveMessage(null);

        verify(consumptionMessageHandlerPort, times(1)).receiveMessage(null);
    }
}
