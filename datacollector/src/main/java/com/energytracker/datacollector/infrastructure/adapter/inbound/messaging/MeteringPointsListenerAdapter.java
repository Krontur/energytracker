package com.energytracker.datacollector.infrastructure.adapter.inbound.messaging;

import com.energytracker.datacollector.application.port.inbound.ManageMeteringPointsFileUseCase;
import com.energytracker.datacollector.application.port.inbound.MeteringPointsListenerPort;
import com.energytracker.datacollector.application.port.outbound.ConfigLoaderPort;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class MeteringPointsListenerAdapter implements MeteringPointsListenerPort {

    private final ManageMeteringPointsFileUseCase manageMeteringPointsFileUseCase;
    private final ConfigLoaderPort configLoaderPort;

    @RabbitListener(queues = "energy-meteringpoints-queue")
    public void receiveMessage(MeteringPoint meteringPoint) {

        log.info("Received message: {}", meteringPoint);
        manageMeteringPointsFileUseCase.addMeteringPointToFile(meteringPoint, configLoaderPort.getProperty("meteringpoints.jsonfile.save.location"));
        log.info("Metering points saved to file");
    }

}
