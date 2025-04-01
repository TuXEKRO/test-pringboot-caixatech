package com.round3.realestate.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static com.round3.realestate.messaging.BidPublisher.*;

@Configuration
@AllArgsConstructor
public class RabbitMQConfiguration {

    @Bean
    public Map<String, Exchange> createExchanges(AmqpAdmin amqpAdmin) {
        Exchange exchange = ExchangeBuilder.directExchange(RABBIT_EXCHANGE).build();
        amqpAdmin.declareExchange(exchange);
        return Map.of(RABBIT_EXCHANGE, exchange);
    }

    @Bean
    public Map<String, Queue> createQueues(AmqpAdmin amqpAdmin) {
        Queue q = QueueBuilder.durable(RABBIT_QUEUE).build();
        amqpAdmin.declareQueue(q);
        return Map.of(RABBIT_QUEUE, q);
    }

    @Bean
    public List<Binding> createBindings(AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(
                createQueues(amqpAdmin).get(RABBIT_QUEUE)
            ).to(
                createExchanges(amqpAdmin).get(RABBIT_EXCHANGE)
            ).with(RABBIT_ROUTING_KEY).noargs();
        amqpAdmin.declareBinding(binding);
        return List.of(binding);
    }
}
