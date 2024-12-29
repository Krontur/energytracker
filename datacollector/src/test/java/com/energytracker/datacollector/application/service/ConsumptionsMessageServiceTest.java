package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.dto.ConsumptionResponseDto;
import com.energytracker.datacollector.application.mapper.ConsumptionMapper;
import com.energytracker.datacollector.application.port.outbound.QueueMessagingPort;
import com.energytracker.datacollector.domain.model.ActionType;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.NoContentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ConsumptionsMessageServiceTest {

    @Mock
    private QueueMessagingPort queueMessagingPort;

    @Mock
    private Binding consumptionsBinding;

    @MockBean
    private ConsumptionsMessageService consumptionsMessageService;

    @Mock
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumptionsMessageService = new ConsumptionsMessageService(queueMessagingPort, consumptionsBinding);
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testSendMessage_withEmptyConsumptionResultList_throwsNoContentException() {
        List<ConsumptionResult> emptyList = Collections.emptyList();
        assertThrows(
                jakarta.ws.rs.core.NoContentException.class,
                () -> consumptionsMessageService.sendMessage(emptyList, "testQueue")
        );
        verifyNoInteractions(queueMessagingPort);
    }

    @Test
    void testSendMessage_withNullConsumptionResultList_throwsNoContentException() {
        assertThrows(
                jakarta.ws.rs.core.NoContentException.class,
                () -> consumptionsMessageService.sendMessage(null, "testQueue")
        );
        verifyNoInteractions(queueMessagingPort);
    }

}
