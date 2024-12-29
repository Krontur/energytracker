package com.energytracker.datacollector.infrastructure.adapter.inbound.messaging;

import com.energytracker.datacollector.application.port.inbound.ManageMeteringPointsFileUseCase;
import com.energytracker.datacollector.domain.model.ActionType;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.Mockito.*;

class MeteringPointsListenerAdapterTest {

    @Mock
    private ManageMeteringPointsFileUseCase manageMeteringPointsFileUseCase;

    private MeteringPointsListenerAdapter listenerAdapter;

    @Value("/test/location")
    private String meteringPointsFileLocation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listenerAdapter = new MeteringPointsListenerAdapter(manageMeteringPointsFileUseCase);
    }

    @Test
    void testReceiveMessage_addAction() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.ADD);

        listenerAdapter.receiveMessage(meteringPoint);

        verify(manageMeteringPointsFileUseCase, times(1))
                .addMeteringPointToFile(eq(meteringPoint), eq(meteringPointsFileLocation));
        verify(manageMeteringPointsFileUseCase, never())
                .deleteMeteringPointFromFile(any(), any());
    }

    @Test
    void testReceiveMessage_deleteAction() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.DELETE);

        listenerAdapter.receiveMessage(meteringPoint);

        verify(manageMeteringPointsFileUseCase, times(1))
                .deleteMeteringPointFromFile(eq(meteringPoint), eq(meteringPointsFileLocation));
        verify(manageMeteringPointsFileUseCase, never())
                .addMeteringPointToFile(any(), any());
    }

    @Test
    void testReceiveMessage_invalidAction() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(null);

        listenerAdapter.receiveMessage(meteringPoint);

        verify(manageMeteringPointsFileUseCase, never())
                .addMeteringPointToFile(any(), any());
        verify(manageMeteringPointsFileUseCase, never())
                .deleteMeteringPointFromFile(any(), any());
    }

    @Test
    void testReceiveMessage_exceptionHandling() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.ADD);

        doThrow(new RuntimeException("Test Exception"))
                .when(manageMeteringPointsFileUseCase)
                .addMeteringPointToFile(any(), any());

        listenerAdapter.receiveMessage(meteringPoint);

        verify(manageMeteringPointsFileUseCase, times(1))
                .addMeteringPointToFile(eq(meteringPoint), eq(meteringPointsFileLocation));
    }
}
