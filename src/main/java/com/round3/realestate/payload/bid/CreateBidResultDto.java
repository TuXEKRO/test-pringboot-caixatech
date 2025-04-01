package com.round3.realestate.payload.bid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBidResultDto {

    private final Boolean success = true;
    private final String message = SUCCESS_MESSAGE;

    private static final String SUCCESS_MESSAGE = "Bid submitted successfully.";
}
