package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointListenerPort;
import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class MeteringPointListenerAdapter implements MeteringPointListenerPort {

    private final MeteringPointMessageHandlerPort meteringPointMessageHandlerPort;

    @RabbitListener(queues = "${rabbitmq.queue.new.meteringpoint}")
    public void receiveMessage(MeteringPoint meteringPoint) {
        log.info("Received message: {}", meteringPoint);
        meteringPointMessageHandlerPort.receiveMessage(meteringPoint);
    }

}
