package com.round3.realestate.payload.bid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateBidDto {
    @NotNull
    @Positive
    private BigDecimal bidAmount;
}
