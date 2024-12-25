package com.energytracker.datacollector.infrastructure.adapter.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueueName;

    @Value("${rabbitmq.exchange.consumptions}")
    private String consumptionsExchangeName;

    @Value("${rabbitmq.routing.key.consumptions}")
    private String consumptionsRoutingKey;

    @Bean
    public Queue consumptionsQueue() {
        return new Queue(consumptionsQueueName, true);
    }

    @Bean
    public DirectExchange consumptionsExchange() {
        return new DirectExchange(consumptionsExchangeName);
    }

    @Bean
    public Binding consumptionsBinding(Queue consumptionsQueue, DirectExchange consumptionsExchange) {
        return BindingBuilder.bind(consumptionsQueue).to(consumptionsExchange).with(consumptionsRoutingKey);
    }

    @Value("${rabbitmq.queue.meteringpoint}")
    private String meteringPointQueueName;

    @Value("${rabbitmq.exchange.meteringpoint}")
    private String meteringPointExchangeName;

    @Value("${rabbitmq.routing.key.meteringpoint}")
    private String meteringPointRoutingKey;

    @Bean
    public Queue meteringPointQueue() {
        return new Queue(meteringPointQueueName, true);
    }

    @Bean
    public DirectExchange meteringPointExchange() {
        return new DirectExchange(meteringPointExchangeName);
    }

    @Bean
    public Binding meteringPointBinding(Queue meteringPointQueue, DirectExchange meteringPointExchange) {
        return BindingBuilder.bind(meteringPointQueue).to(meteringPointExchange).with(meteringPointRoutingKey);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
