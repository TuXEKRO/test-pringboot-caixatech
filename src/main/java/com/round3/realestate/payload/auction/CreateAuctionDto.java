package com.round3.realestate.payload.auction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateAuctionDto {
    @NotNull
    private final Long propertyId;
    @NotNull
    private final LocalDateTime startTime;
    @NotNull
    private final LocalDateTime endTime;
    @NotNull
    @Positive
    private final BigDecimal minIncrement;
    @NotNull
    @Positive
    private final BigDecimal startingPrice;
}
