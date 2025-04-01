package com.round3.realestate.payload.bid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BidAuctionDto {
    private Long id;
    private BigDecimal bidAmount;
    private LocalDateTime timestamp;
    private Long userId;
}
