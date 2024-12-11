package com.energytracker.devicecatalog.infrastructure.adapter.outbound.messaging.rabbitmq;

import com.energytracker.devicecatalog.application.port.outbound.QueueMessagingPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
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
    public void sendMessage(String queueExchange, String queueRoutingKey, Message message) {
        try {
            log.info("Sending message: {}", message.toString());
            rabbitTemplate.send(queueExchange, queueRoutingKey, message);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }

    }
}
