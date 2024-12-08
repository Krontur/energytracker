package com.energytracker.consumptionservice.infrastructure.adapter.configuration;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointListenerPort;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQListenerConfig {

    @Value("${rabbitmq.queue.new.meteringpoint}")
    private String newMeteringPointQueueName;

    @Bean
    public MessageListenerAdapter messageListenerAdapter(MeteringPointListenerPort meteringPointListenerPort) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(
                meteringPointListenerPort, "receiveMessage");
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
        container.setQueueNames(newMeteringPointQueueName);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

}
