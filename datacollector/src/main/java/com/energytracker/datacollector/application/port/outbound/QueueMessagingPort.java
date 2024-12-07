package com.energytracker.datacollector.application.port.outbound;

public interface QueueMessagingPort {

    void sendMessage(String message, String queueName);

}
