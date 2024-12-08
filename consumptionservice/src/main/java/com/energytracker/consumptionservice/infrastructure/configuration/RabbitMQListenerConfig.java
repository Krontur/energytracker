package com.energytracker.consumptionservice.infrastructure.configuration;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointsListenerPort;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQListenerConfig {

    @Bean
    public MessageListenerAdapter messageListenerAdapter(MeteringPointsListenerPort meteringPointsListenerPort) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(
                meteringPointsListenerPort, "receiveMessage");
        messageListenerAdapter.setMessageConverter(messageConverter());
        return messageListenerAdapter;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames( "energy-meteringpoints-queue");
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

}
