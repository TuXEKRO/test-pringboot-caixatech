package com.round3.realestate.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BidPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(BidMessage message) {
        rabbitTemplate.convertAndSend(
            RABBIT_EXCHANGE,
            RABBIT_ROUTING_KEY,
            message
        );
    }

    public static final String RABBIT_QUEUE = "bid.queue";
    public static final String RABBIT_EXCHANGE = "bid.exchange";
    public static final String RABBIT_ROUTING_KEY = "bid.routingkey";
}
