package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.QueueMessagingPort;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ConsumptionsMessageService implements ConsumptionsMessageHandlerPort {

    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;

    public ConsumptionsMessageService(QueueMessagingPort queueMessagingPort) {
        this.queueMessagingPort = queueMessagingPort;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void sendMessage(List<ConsumptionResult> consumptionResultList, String queueName) {
        String consumptionResultMessage = null;
        try {
            consumptionResultMessage = objectMapper.writeValueAsString(consumptionResultList);
            log.info("Sending Message to Queue: {}", consumptionResultMessage);
            queueMessagingPort.sendMessage(consumptionResultMessage, queueName);
        } catch (JsonProcessingException e) {
            log.error("Error converting consumption result list to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize consumption results", e);
        }
    }

}
