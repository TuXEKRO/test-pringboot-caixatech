package com.round3.realestate.service;

import com.round3.realestate.entity.*;
import com.round3.realestate.exception.AuctionBidMustNotExceedMinimumException;
import com.round3.realestate.exception.MortgageMissingInformationException;
import com.round3.realestate.messaging.BidMessage;
import com.round3.realestate.messaging.BidPublisher;
import com.round3.realestate.payload.bid.CreateBidDto;
import com.round3.realestate.payload.bid.CreateBidResultDto;
import com.round3.realestate.repository.AuctionRepository;
import com.round3.realestate.repository.BidRepository;
import com.round3.realestate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceImplTest {

    @Mock
    BidRepository bidRepository;

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BidPublisher bidPublisher;

    @InjectMocks
    BidServiceImpl bidService;

    @Captor
    ArgumentCaptor<BidMessage> messageCaptor;

    private UserDetails details;
    private User user;
    private Auction auction;
    private Property availableProperty;
    private CreateBidDto bidTooSmallDto;
    private CreateBidDto normalBidDto;

    @BeforeEach
    public void setUp() {
        details = mock(UserDetails.class);
        given(details.getUsername()).willReturn("user");

        user = User.builder()
            .id(1L)
            .username("User")
            .password("Password")
            .email("user@org.com")
            .build();

        auction = Auction.builder()
            .startingPrice(BigDecimal.valueOf(300_000))
            .minIncrement(BigDecimal.valueOf(5_000))
            .id(1L)
            .property(availableProperty)
            .currentHighestBid(BigDecimal.valueOf(340_000))
            .build();

        availableProperty = Property.builder()
            .availability(PropertyAvailability.Available)
            .id(1L)
            .price(BigDecimal.valueOf(680_000))
            .size("size")
            .rooms("rooms")
            .name("name")
            .location("location")
            .build();

        bidTooSmallDto = new CreateBidDto();
        bidTooSmallDto.setBidAmount(BigDecimal.valueOf(343_000));

        normalBidDto = new CreateBidDto();
        normalBidDto.setBidAmount(BigDecimal.valueOf(347_000));
    }

    @Test
    public void small_bid_should_send_message_and_throw() {
        // given
        given(userRepository.getUserByUsername(any()))
            .willReturn(Optional.of(user));

        given(auctionRepository.findById(any()))
            .willReturn(Optional.of(auction));

        assertThrows(
            AuctionBidMustNotExceedMinimumException.class,
            // when
            () -> bidService.bid(bidTooSmallDto, auction.getId(), details),
            "Expected to throw AuctionBidMustNotExceedMinimumException"
        );

        // then
        verify(bidPublisher).sendMessage(messageCaptor.capture());

        BidMessage capturedMessage = messageCaptor.getValue();

        assertEquals(
            capturedMessage.getBidAmount(),
            bidTooSmallDto.getBidAmount()
        );

        assertEquals(
            capturedMessage.getAuctionId(),
            auction.getId()
        );

        assertEquals(
            capturedMessage.getUserId(),
            user.getId()
        );

        then(userRepository).should().getUserByUsername("user");
        then(auctionRepository).should().findById(1L);
    }

    @Test
    public void normal_bid_should_send_message_and_return() {
        // given
        given(userRepository.getUserByUsername(any()))
            .willReturn(Optional.of(user));

        given(auctionRepository.findById(any()))
            .willReturn(Optional.of(auction));

        // when
        CreateBidResultDto result = bidService.bid(normalBidDto, auction.getId(), details);

        // then
        assertEquals(
            result.getSuccess(),
            true
        );

        then(userRepository).should().getUserByUsername("user");
        then(auctionRepository).should().findById(1L);
    }
}
