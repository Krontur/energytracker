package com.energytracker.datacollector.infrastructure.adapter.inbound.messaging;

import com.energytracker.datacollector.application.port.inbound.ManageMeteringPointsFileUseCase;
import com.energytracker.datacollector.application.port.inbound.MeteringPointsListenerPort;
import com.energytracker.datacollector.application.port.outbound.ConfigLoaderPort;
import com.energytracker.datacollector.domain.model.ActionType;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class MeteringPointsListenerAdapter implements MeteringPointsListenerPort {

    private final ManageMeteringPointsFileUseCase manageMeteringPointsFileUseCase;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsFileLocation;

    @RabbitListener(queues = "${rabbitmq.queue.meteringpoint}")
    public void receiveMessage(MeteringPoint meteringPoint) {
        try {
            log.info("Received message: {}", meteringPoint);
            handleAction(meteringPoint);
            log.info("Metering points saved to file");
        } catch (Exception e) {
            log.error("Error saving metering points to file", e);
        }
    }

    private void handleAction(MeteringPoint meteringPoint) {
        log.info("Handle action of received message: {}", meteringPoint);
        switch (meteringPoint.getActionType()) {
            case ADD:
                manageMeteringPointsFileUseCase.addMeteringPointToFile(meteringPoint, meteringPointsFileLocation);
                break;
            case DELETE:
                manageMeteringPointsFileUseCase.deleteMeteringPointFromFile(meteringPoint, meteringPointsFileLocation);
                break;
            default:
                log.error("Invalid action: {}", meteringPoint.getActionType());
        }
    }

}
