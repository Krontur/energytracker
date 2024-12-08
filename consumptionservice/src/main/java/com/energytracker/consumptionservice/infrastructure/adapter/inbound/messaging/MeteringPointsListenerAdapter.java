package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointsListenerPort;
import com.energytracker.consumptionservice.application.port.inbound.MeteringPointsMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.outbound.ConfigLoaderPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class MeteringPointsListenerAdapter implements MeteringPointsListenerPort {

    private final MeteringPointsMessageHandlerPort meteringPointsMessageHandlerPort;
    private final ConfigLoaderPort configLoaderPort;

    @RabbitListener(queues = "energy-meteringpoints-queue")
    public void receiveMessage(List<MeteringPoint> meteringPoints) {

        log.info("Received message: {}", meteringPoints);
        meteringPointsMessageHandlerPort.receiveMessage(meteringPoints, configLoaderPort.getProperty("meteringpoints.jsonfile.save.location"));
        log.info("Metering points saved to file");
    }

}
