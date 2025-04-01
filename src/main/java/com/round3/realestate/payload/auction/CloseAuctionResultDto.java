package com.round3.realestate.payload.auction;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CloseAuctionResultDto {

    private final BigDecimal winningBid;
    private final Boolean success = true;
    private final Long winningUserId;
    private final String message = SUCCESS;

    private CloseAuctionResultDto(
        BigDecimal winningBid,
        Long winningUserId
    ) {
        this.winningBid = winningBid;
        this.winningUserId = winningUserId;
    }

    public static CloseAuctionResultDto success() {
        return new CloseAuctionResultDto(
            null,
            null
        );
    }

    public static CloseAuctionResultDto success(
        BigDecimal winningBid,
        Long winningUserId
    ) {
        return new CloseAuctionResultDto(
            winningBid,
            winningUserId
        );
    }

    private static final String SUCCESS = "Auction closed successfully.";
}
