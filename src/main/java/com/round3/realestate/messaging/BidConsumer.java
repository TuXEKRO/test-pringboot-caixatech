package com.round3.realestate.messaging;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.repository.AuctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.round3.realestate.messaging.BidPublisher.RABBIT_QUEUE;

@Service
@AllArgsConstructor
public class BidConsumer {

    private final AuctionRepository auctionRepository;

    @RabbitListener(queues = RABBIT_QUEUE)
    public void receiveMessage(BidMessage message) {

        Auction auction = auctionRepository.findById(message.getAuctionId())
            .orElse(null);

        if (auction != null) {
            BigDecimal highestBid = auction.getCurrentHighestBid();
            BigDecimal bidAmount = message.getBidAmount();
            BigDecimal minIncrement = auction.getMinIncrement();
            if (bidAmount != null &&
                bidAmount.compareTo(highestBid.add(minIncrement)) >= 0
            ) {
                auction.setCurrentHighestBid(bidAmount);
                auctionRepository.save(auction);
            }
        }
    }
}
