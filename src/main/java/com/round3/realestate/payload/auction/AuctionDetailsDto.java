package com.round3.realestate.payload.auction;

import com.round3.realestate.entity.AuctionStatus;
import com.round3.realestate.payload.bid.BidAuctionDto;
import com.round3.realestate.payload.dashboard.DashboardPropertyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class AuctionDetailsDto {
    private Long id;
    private DashboardPropertyDto property;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    private BigDecimal minIncrement;
    private BigDecimal startingPrice;
    private BigDecimal currentHighestBid;
    private List<BidAuctionDto> bids;
}
