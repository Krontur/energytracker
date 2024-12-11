package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.dto.ConsumptionResponseDto;
import com.energytracker.datacollector.application.mapper.ConsumptionMapper;
import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.QueueMessagingPort;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.core.NoContentException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ConsumptionsMessageService implements ConsumptionsMessageHandlerPort {

    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;
    private final Binding consumptionsBinding;

    public ConsumptionsMessageService(QueueMessagingPort queueMessagingPort, Binding consumptionsBinding) {
        this.queueMessagingPort = queueMessagingPort;this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.consumptionsBinding = consumptionsBinding;
    }

    @Override
    public void sendMessage(List<ConsumptionResult> consumptionResultList, String queueName) {

        List<ConsumptionResponseDto> consumptionResponseDtos = new ArrayList<>();

        try {
            if (consumptionResultList == null || consumptionResultList.isEmpty()) {
                log.warn("Parameter consumptionResultList is null or empty");
                throw new NoContentException("Parameter consumptionResultList is null or empty");
            }
            log.info("Mapping consumptions result list to response dto");
            consumptionResultList.forEach( consumptionResult -> {
                consumptionResponseDtos.add(
                        ConsumptionMapper.consumptionResultDomainToResponseDto(consumptionResult));
            });
            String consumptionResultMessage = null;

            log.info("Mapping consumptions response dto to Message: {}", consumptionResponseDtos);
            consumptionResultMessage = objectMapper.writeValueAsString(consumptionResponseDtos);
            Message message = MessageBuilder
                    .withBody(consumptionResultMessage.getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader("__TypeId__", "java.util.ArrayList")
                    .build();
            log.info("Sending Message to Queue: {}", message);
            queueMessagingPort.sendMessage(consumptionsBinding.getExchange(), consumptionsBinding.getRoutingKey(), message);
        } catch (JsonProcessingException e) {
            log.error("Error converting consumption result list to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize consumption results", e);
        } catch (NoContentException e) {
            log.warn(e);
        } catch (Exception e){

        }
    }

}
