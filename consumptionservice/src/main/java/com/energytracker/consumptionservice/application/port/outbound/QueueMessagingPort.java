package com.energytracker.consumptionservice.application.port.outbound;

import org.springframework.amqp.core.Message;

public interface QueueMessagingPort {

    void sendMessage(String queueExchange, String queueRoutingKey, Message message);

}
