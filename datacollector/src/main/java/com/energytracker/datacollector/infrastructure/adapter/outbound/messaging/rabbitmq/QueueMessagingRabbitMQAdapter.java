package com.energytracker.datacollector.infrastructure.adapter.outbound.messaging.rabbitmq;

import com.energytracker.datacollector.application.port.outbound.QueueMessagingPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class QueueMessagingRabbitMQAdapter implements QueueMessagingPort {

    private final RabbitTemplate rabbitTemplate;

    public QueueMessagingRabbitMQAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(String message, String queueName) {
        try {
            log.info("Sending message: {}", message);
            rabbitTemplate.convertAndSend(queueName, message);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }
}
