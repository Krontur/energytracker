package com.energytracker.devicecatalog.infrastructure.adapter.configuration;

import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.new.meteringpoint}")
    private String newMeteringPointQueueName;

    @Value("${rabbitmq.exchange.new.meteringpoint}")
    private String newMeteringPointExchangeName;

    @Value("${rabbitmq.routing.key.new.meteringpoint}")
    private String newMeteringPointRoutingKey;

    @Bean
    public Queue newMeteringPointQueue() {
        return new Queue(newMeteringPointQueueName, true);
    }

    @Bean
    public DirectExchange newMeteringPointExchange() {
        return new DirectExchange(newMeteringPointExchangeName);
    }

    @Bean
    public Binding newMeteringPointBinding(Queue newMeteringPointQueue, DirectExchange newMeteringPointExchange) {
        return BindingBuilder.bind(newMeteringPointQueue).to(newMeteringPointExchange).with(newMeteringPointRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("Message sent and acknowledged.");
            } else {
                log.error("Message not delivered: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
