package com.round3.realestate.converter;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.entity.Bid;
import com.round3.realestate.entity.User;
import com.round3.realestate.messaging.BidMessage;
import com.round3.realestate.payload.auction.CloseAuctionResultDto;
import com.round3.realestate.payload.bid.BidAuctionDto;
import com.round3.realestate.payload.bid.CreateBidDto;

import java.time.LocalDateTime;

public class BidConverter {

    public static BidMessage toMessage(CreateBidDto dto, Auction auction, User user) {
        return new BidMessage(
            auction.getId(),
            user.getId(),
            dto.getBidAmount(),
            LocalDateTime.now()
        );
    }

    public static Bid toEntity(BidMessage message, Auction auction, User user) {
        return Bid.builder()
            .auction(auction)
            .bidAmount(message.getBidAmount())
            .timestamp(message.getTimestamp())
            .user(user)
            .build();
    }

    public static Bid toEntity(CreateBidDto dto, Auction auction, User user) {
        return Bid.builder()
            .auction(auction)
            .bidAmount(dto.getBidAmount())
            .timestamp(LocalDateTime.now())
            .user(user)
            .build();
    }

    public static CloseAuctionResultDto toCloseAuctionResultDto(Bid bid) {
        return CloseAuctionResultDto.success(
            bid.getBidAmount(),
            bid.getUser().getId()
        );
    }

    public static BidAuctionDto toBidAuctionDto(Bid bid) {
        return BidAuctionDto.builder()
            .id(bid.getId())
            .bidAmount(bid.getBidAmount())
            .timestamp(bid.getTimestamp())
            .userId(bid.getUser().getId())
            .build();
    }
}
