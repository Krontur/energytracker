package com.energytracker.consumptionservice.infrastructure.adapter.outbound.messaging.rabbitmq;

import com.energytracker.consumptionservice.application.port.outbound.QueueMessagingPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class QueueMessagingRabbitMQAdapter implements QueueMessagingPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String queueExchange, String queueRoutingKey, Message message) {
        try {
            log.info("Sending message: {}", message);
            rabbitTemplate.send(queueExchange, queueRoutingKey, message);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }
}
