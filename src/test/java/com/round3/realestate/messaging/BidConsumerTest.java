package com.round3.realestate.messaging;

import com.round3.realestate.entity.*;
import com.round3.realestate.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidConsumerTest {
    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    BidConsumer bidConsumer;

    @Captor
    ArgumentCaptor<Auction> auctionCaptor;

    private Auction auction;
    private BidMessage lowerThanHighestBid;
    private BidMessage highestBid;

    @BeforeEach
    public void setUp() {

        Property availableProperty = Property.builder()
            .availability(PropertyAvailability.Available)
            .id(1L)
            .price(BigDecimal.valueOf(680_000))
            .size("size")
            .rooms("rooms")
            .name("name")
            .location("location")
            .build();

        auction = Auction.builder()
            .startingPrice(BigDecimal.valueOf(300_000))
            .minIncrement(BigDecimal.valueOf(5_000))
            .id(1L)
            .property(availableProperty)
            .currentHighestBid(BigDecimal.valueOf(340_000))
            .build();

        lowerThanHighestBid = new BidMessage(
            1L, 2L, BigDecimal.valueOf(337_000), LocalDateTime.now()
        );

        highestBid = new BidMessage(
            1L, 2L, BigDecimal.valueOf(347_000), LocalDateTime.now()
        );
    }

    @Test
    public void for_lower_bid_should_not_set_auction_highest () {
        // given
        given(auctionRepository.findById(any()))
            .willReturn(Optional.of(auction));

        // when
        bidConsumer.receiveMessage(lowerThanHighestBid);

        // then
        verifyNoMoreInteractions(auctionRepository);
    }

    @Test
    public void should_save_highest_bid () {
        // given
        given(auctionRepository.findById(any()))
            .willReturn(Optional.of(auction));

        // when
        bidConsumer.receiveMessage(highestBid);

        // then
        verify(auctionRepository).save(auctionCaptor.capture());

        Auction capturedAuction = auctionCaptor.getValue();

        assertEquals(
            capturedAuction.getCurrentHighestBid(),
            highestBid.getBidAmount()
        );
    }
}
