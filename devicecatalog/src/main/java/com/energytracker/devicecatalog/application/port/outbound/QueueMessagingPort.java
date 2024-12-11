package com.energytracker.devicecatalog.application.port.outbound;

import org.springframework.amqp.core.Message;

public interface QueueMessagingPort {

    void sendMessage(String queueExchange, String queueRoutingKey, Message message);

}
