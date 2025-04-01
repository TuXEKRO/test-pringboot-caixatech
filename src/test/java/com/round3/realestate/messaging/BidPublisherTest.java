package com.round3.realestate.messaging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.round3.realestate.messaging.BidPublisher.RABBIT_EXCHANGE;
import static com.round3.realestate.messaging.BidPublisher.RABBIT_ROUTING_KEY;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class BidPublisherTest {

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    BidPublisher bidPublisher;

    @Test
    public void should_send_message() {
        // given
        BidMessage message = new BidMessage(
            1L, 2L, BigDecimal.valueOf(300_000), LocalDateTime.now()
        );

        // when
        bidPublisher.sendMessage(message);

        // then
        then(rabbitTemplate).should().convertAndSend(
            RABBIT_EXCHANGE,
            RABBIT_ROUTING_KEY,
            message);
    }
}
