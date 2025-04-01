package com.round3.realestate.payload.auction;

import lombok.Getter;

@Getter
public class CreateAuctionResultDto {

    private final String message = SUCCESS;
    private final Long auctionId;
    private final Boolean success = true;

    private CreateAuctionResultDto(Long auctionId) {
        this.auctionId = auctionId;
    }


    public static CreateAuctionResultDto success(Long auctionId) {
        return new CreateAuctionResultDto(auctionId);
    }

    private static final String SUCCESS = "Auction created successfully.";
}
