package com.energytracker.consumptionservice.infrastructure.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    @Value("${rabbitmq.queue.meteringpoints}")
    private String meteringPointsQueueName;

    @Value("${rabbitmq.exchange.meteringpoints}")
    private String meteringPointsExchangeName;

    @Value("${rabbitmq.routing.key.meteringpoints}")
    private String meteringPointsRoutingKey;

    @Bean
    public Queue meteringPointsQueue() {
        return new Queue(meteringPointsQueueName, true);
    }

    @Bean
    public DirectExchange meteringPointsExchange() {
        return new DirectExchange(meteringPointsExchangeName);
    }

    @Bean
    public Binding meteringPointsBinding(Queue meteringPointsQueue, DirectExchange meteringPointsExchange) {
        return BindingBuilder.bind(meteringPointsQueue).to(meteringPointsExchange).with(meteringPointsRoutingKey);
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
