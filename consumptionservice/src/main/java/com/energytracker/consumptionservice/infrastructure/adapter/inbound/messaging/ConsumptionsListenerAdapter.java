package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.ConsumptionMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.inbound.ConsumptionsListenerPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConsumptionsListenerAdapter implements ConsumptionsListenerPort {

    private final ConsumptionMessageHandlerPort consumptionMessageHandlerPort;

    @RabbitListener(queues = "${rabbitmq.queue.consumptions}")
    public void receiveMessage(List<Consumption> consumptions) {
        log.info("Received message: {}", consumptions);
        consumptionMessageHandlerPort.receiveMessage(consumptions);
    }

}
