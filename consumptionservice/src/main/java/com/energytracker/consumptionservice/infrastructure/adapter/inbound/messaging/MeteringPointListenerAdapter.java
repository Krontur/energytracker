package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointListenerPort;
import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.outbound.ConfigLoaderPort;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class MeteringPointListenerAdapter implements MeteringPointListenerPort {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;

    @RabbitListener(queues = "${rabbitmq.queue.new.meteringpoint}")
    public void receiveMessage(MeteringPoint meteringPoint) {
        log.info("Received message: {}", meteringPoint);
        try {
            MeteringPoint savedMeteringPoint = meteringPointRepositoryPort.saveMeteringPoint(meteringPoint);
            if (savedMeteringPoint != null) {
                log.info("Metering point saved: {}", savedMeteringPoint);
            } else {
                log.error("Error saving metering point: {}", meteringPoint);
            }
        } catch (Exception e) {
            log.error("Error saving metering point: {}", meteringPoint, e);
        }
    }

}
