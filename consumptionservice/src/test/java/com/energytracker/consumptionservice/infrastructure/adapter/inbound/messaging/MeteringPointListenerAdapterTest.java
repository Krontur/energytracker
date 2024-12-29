package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.domain.model.ActionType;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MeteringPointListenerAdapterTest {

    @Mock
    private MeteringPointMessageHandlerPort meteringPointMessageHandlerPort;

    @InjectMocks
    private MeteringPointListenerAdapter meteringPointListenerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage() {
        MeteringPoint meteringPoint = new MeteringPoint(
                ActionType.ADD,
                10L,
                "A5",
                34
        );

        meteringPointListenerAdapter.receiveMessage(meteringPoint);

        ArgumentCaptor<MeteringPoint> captor = ArgumentCaptor.forClass(MeteringPoint.class);
        verify(meteringPointMessageHandlerPort, times(1)).receiveMessage(captor.capture());

        MeteringPoint capturedMeteringPoint = captor.getValue();
        assertEquals(meteringPoint, capturedMeteringPoint);
    }

    @Test
    void testReceiveMessageWithNull() {
        meteringPointListenerAdapter.receiveMessage(null);

        verify(meteringPointMessageHandlerPort, times(1)).receiveMessage(null);
    }
}
