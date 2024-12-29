package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.application.port.outbound.QueueMessagingPort;
import com.energytracker.consumptionservice.domain.model.ActionType;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MeteringPointMessageServiceTest {

    @Mock
    private MeteringPointRepositoryPort meteringPointRepositoryPort;

    @Mock
    private QueueMessagingPort queueMessagingPort;

    @Mock
    private Binding meteringPointBinding;

    private MeteringPointMessageService meteringPointMessageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meteringPointMessageService = new MeteringPointMessageService(
                meteringPointRepositoryPort,
                queueMessagingPort,
                meteringPointBinding
        );
        when(meteringPointBinding.getExchange()).thenReturn("exchange");
        when(meteringPointBinding.getRoutingKey()).thenReturn("routingKey");
    }

    @Test
    void testReceiveMessage_addAction() throws Exception {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.ADD);

        when(meteringPointRepositoryPort.saveMeteringPoint(any())).thenReturn(meteringPoint);

        meteringPointMessageService.receiveMessage(meteringPoint);

        verify(meteringPointRepositoryPort, times(1)).saveMeteringPoint(meteringPoint);
        verify(queueMessagingPort, times(1)).sendMessage(eq("exchange"), eq("routingKey"), any(Message.class));

        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(queueMessagingPort).sendMessage(eq("exchange"), eq("routingKey"), messageCaptor.capture());

        Message sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(MessageProperties.CONTENT_TYPE_JSON, sentMessage.getMessageProperties().getContentType());
        assertEquals(objectMapper.writeValueAsString(meteringPoint), new String(sentMessage.getBody(), StandardCharsets.UTF_8));
    }

    @Test
    void testReceiveMessage_deleteAction() throws Exception {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.DELETE);

        when(meteringPointRepositoryPort.deleteMeteringPoint(any())).thenReturn(meteringPoint);

        meteringPointMessageService.receiveMessage(meteringPoint);

        verify(meteringPointRepositoryPort, times(1)).deleteMeteringPoint(meteringPoint);
        verify(queueMessagingPort, times(1)).sendMessage(eq("exchange"), eq("routingKey"), any(Message.class));
    }

    @Test
    void testReceiveMessage_nullMessage() {
        meteringPointMessageService.receiveMessage(null);

        verifyNoInteractions(meteringPointRepositoryPort);
        verifyNoInteractions(queueMessagingPort);
    }

    @Test
    void testReceiveMessage_unhandledAction() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(null);

        meteringPointMessageService.receiveMessage(meteringPoint);

        verifyNoInteractions(meteringPointRepositoryPort);
        verifyNoInteractions(queueMessagingPort);
    }

    @Test
    void testReceiveMessage_repositoryException() {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.ADD);

        doThrow(new RuntimeException("Database error")).when(meteringPointRepositoryPort).saveMeteringPoint(any());

        assertDoesNotThrow(() -> meteringPointMessageService.receiveMessage(meteringPoint));
        verify(meteringPointRepositoryPort, times(1)).saveMeteringPoint(any());
        verifyNoInteractions(queueMessagingPort);
    }

    @Test
    void testReceiveMessage_queueException() throws Exception {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setActionType(ActionType.ADD);

        when(meteringPointRepositoryPort.saveMeteringPoint(any())).thenReturn(meteringPoint);
        doThrow(new RuntimeException("Queue error")).when(queueMessagingPort).sendMessage(any(), any(), any());

        assertDoesNotThrow(() -> meteringPointMessageService.receiveMessage(meteringPoint));
        verify(meteringPointRepositoryPort, times(1)).saveMeteringPoint(any());
        verify(queueMessagingPort, times(1)).sendMessage(any(), any(), any());
    }
}