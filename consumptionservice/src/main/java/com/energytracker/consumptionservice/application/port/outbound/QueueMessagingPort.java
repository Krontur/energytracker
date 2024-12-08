package com.energytracker.consumptionservice.application.port.outbound;

public interface QueueMessagingPort {

    void sendMessage(String message, String queueName);

}
