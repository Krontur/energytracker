package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.application.port.outbound.QueueMessagingPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MeteringPointMessageService implements MeteringPointMessageHandlerPort {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;
    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;

    public MeteringPointMessageService(MeteringPointRepositoryPort meteringPointRepositoryPort, QueueMessagingPort queueMessagingPort) {
        this.meteringPointRepositoryPort = meteringPointRepositoryPort;
        this.queueMessagingPort = queueMessagingPort;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void receiveMessage(MeteringPoint meteringPoint) {

        log.info("Received message: {}", meteringPoint);

        if (meteringPoint == null) {
            log.error("Received a null MeteringPoint. Ignoring message.");
            return;
        }

        try {
            MeteringPoint savedMeteringPoint = handleAction(meteringPoint);

            if (savedMeteringPoint != null) {
                log.info("Metering point processed successfully: {}", savedMeteringPoint);
                queueMessagingPort.sendMessage(objectMapper.writeValueAsString(savedMeteringPoint), "${rabbitmq.queue.meteringpoint}");
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
        return meteringPointRepositoryPort.saveMeteringPoint(meteringPoint);
    }

    private MeteringPoint handleDeleteAction(MeteringPoint meteringPoint) {
        log.info("Handling DELETE action for metering point: {}", meteringPoint);
        return meteringPointRepositoryPort.deleteMeteringPoint(meteringPoint);
    }

}
