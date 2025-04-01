package com.round3.realestate.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BidMessage {
    private final Long auctionId;
    private final Long userId;
    private final BigDecimal bidAmount;
    private final LocalDateTime timestamp;
}
