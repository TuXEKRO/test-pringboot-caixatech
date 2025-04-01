package com.round3.realestate.service;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.entity.Property;
import com.round3.realestate.entity.PropertyAvailability;
import com.round3.realestate.exception.AuctionPropertyNotAvailableException;
import com.round3.realestate.exception.AuctionPropertyNotFoundException;
import com.round3.realestate.payload.auction.CreateAuctionDto;
import com.round3.realestate.payload.auction.CreateAuctionResultDto;
import com.round3.realestate.repository.AuctionRepository;
import com.round3.realestate.repository.BidRepository;
import com.round3.realestate.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceImplTest {

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    PropertyRepository propertyRepository;

    @Mock
    BidRepository bidRepository;

    @InjectMocks
    AuctionServiceImpl auctionService;

    private CreateAuctionDto createAuctionDto;

    private Property availableProperty;
    private Property unavailableProperty;
    private Auction auction;

    @BeforeEach
    public void setUp() {
        createAuctionDto = new CreateAuctionDto(
            1L,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(2),
            BigDecimal.valueOf(5000),
            BigDecimal.valueOf(600_000)
        );

        unavailableProperty = Property.builder()
            .availability(PropertyAvailability.Unavailable)
            .id(1L)
            .price(BigDecimal.valueOf(680_000))
            .size("size")
            .rooms("rooms")
            .name("name")
            .location("location")
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

        auction = Auction.builder()
            .id(1L)
            .property(availableProperty)
            .build();
    }

    @Test
    public void when_create_with_no_such_property_should_throw() {
        // given
        given(propertyRepository.findById(any())).willReturn(Optional.empty());
        // then
        assertThrows(
            AuctionPropertyNotFoundException.class,
            // when
            () -> auctionService.create(createAuctionDto),
            "Expected to throw AuctionPropertyNotFoundException"
        );
    }

    @Test
    public void when_create_when_property_unavailable_should_throw() {
        // given
        given(propertyRepository.findById(any()))
            .willReturn(Optional.of(unavailableProperty));
        // then
        assertThrows(
            AuctionPropertyNotAvailableException.class,
            // when
            () -> auctionService.create(createAuctionDto),
            "Expected to throw AuctionPropertyNotAvailableException"
        );
    }

    @Test
    public void when_create_with_available_property_should_create() {
        // given
        given(propertyRepository.findById(any()))
            .willReturn(Optional.of(availableProperty));
        given(auctionRepository.save(any())).willReturn(auction);
        // when
        CreateAuctionResultDto result = auctionService.create(createAuctionDto);
        // then
        assertEquals(
            result.getSuccess(),
            true
        );

        assertEquals(
            result.getAuctionId(),
            1L
        );
    }

    @Test
    public void when_close_should_close() {
        // given
        Property property = spy(availableProperty);
        Auction auctionToClose = spy(auction);
    }
}
