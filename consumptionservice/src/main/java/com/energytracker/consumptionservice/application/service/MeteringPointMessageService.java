package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.application.port.outbound.QueueMessagingPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class MeteringPointMessageService implements MeteringPointMessageHandlerPort {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;
    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;
    private final Binding meteringPointBinding;

    public MeteringPointMessageService(MeteringPointRepositoryPort meteringPointRepositoryPort, QueueMessagingPort queueMessagingPort, Binding meteringPointBinding) {
        this.meteringPointRepositoryPort = meteringPointRepositoryPort;
        this.queueMessagingPort = queueMessagingPort;
        this.meteringPointBinding = meteringPointBinding;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    @Transactional
    public void receiveMessage(MeteringPoint meteringPoint) {

        log.info("Received message: {}", meteringPoint);

        if (meteringPoint == null) {
            log.error("Received a null MeteringPoint. Ignoring message.");
            return;
        }

        try {
            MeteringPoint savedMeteringPoint = handleAction(meteringPoint);

            if (savedMeteringPoint != null) {
                String savedMeteringPointString = objectMapper.writeValueAsString(savedMeteringPoint);
                log.info("Metering point processed successfully: {}", savedMeteringPointString);
                Message message = MessageBuilder
                        .withBody(savedMeteringPointString.getBytes(StandardCharsets.UTF_8))
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .build();
                log.info("Sending Message to Queue: {}", message);
                queueMessagingPort.sendMessage(meteringPointBinding.getExchange(), meteringPointBinding.getRoutingKey(), message);
            } else {
                log.error("Failed to process metering point: {}", meteringPoint);
            }
        } catch (Exception e) {
            log.error("Unexpected error processing metering point: {}", meteringPoint, e);
        }

    }
    private MeteringPoint handleAction(MeteringPoint meteringPoint) {
        switch (meteringPoint.getActionType()) {
            case ADD:
                return handleAddAction(meteringPoint);
            case DELETE:
                return handleDeleteAction(meteringPoint);
            default:
                log.warn("Unhandled action type: {} for metering point: {}", meteringPoint.getActionType(), meteringPoint);
                return null;
        }
    }

    private MeteringPoint handleAddAction(MeteringPoint meteringPoint) {
        log.info("Handling ADD action for metering point: {}", meteringPoint);
        MeteringPoint addedMeteringPoint = meteringPointRepositoryPort.saveMeteringPoint(meteringPoint);
        addedMeteringPoint.setActionType(meteringPoint.getActionType());
        log.info("Metering point added successfully: {}", addedMeteringPoint);
        return addedMeteringPoint;
    }

    private MeteringPoint handleDeleteAction(MeteringPoint meteringPoint) {
        log.info("Handling DELETE action for metering point: {}", meteringPoint);
        MeteringPoint deletedMeteringPoint = meteringPointRepositoryPort.deleteMeteringPoint(meteringPoint);
        deletedMeteringPoint.setActionType(meteringPoint.getActionType());
        log.info("Metering point deleted successfully: {}", deletedMeteringPoint);
        return deletedMeteringPoint;
    }

}
