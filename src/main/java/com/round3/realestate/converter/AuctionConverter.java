package com.round3.realestate.converter;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.entity.AuctionStatus;
import com.round3.realestate.entity.Bid;
import com.round3.realestate.entity.Property;
import com.round3.realestate.payload.auction.AuctionDetailsDto;
import com.round3.realestate.payload.auction.CreateAuctionDto;

import java.util.List;

public class AuctionConverter {
    public static Auction toEntity(CreateAuctionDto dto, Property property) {
        return Auction.builder()
            .startTime(dto.getStartTime())
            .endTime(dto.getEndTime())
            .minIncrement(dto.getMinIncrement())
            .startingPrice(dto.getStartingPrice())
            .currentHighestBid(dto.getStartingPrice())
            .property(property)
            .status(AuctionStatus.open)
            .build();
    }

    public static AuctionDetailsDto toDto(Auction auction, List<Bid> bids) {
        return AuctionDetailsDto.builder()
            .id(auction.getId())
            .property(PropertyConverter.toDto(auction.getProperty()))
            .startTime(auction.getStartTime())
            .endTime(auction.getEndTime())
            .status(auction.getStatus())
            .minIncrement(auction.getMinIncrement())
            .startingPrice(auction.getStartingPrice())
            .currentHighestBid(auction.getCurrentHighestBid())
            .bids(bids.stream().map(BidConverter::toBidAuctionDto).toList())
            .build();
    }
}
