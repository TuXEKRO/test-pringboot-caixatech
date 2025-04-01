package com.round3.realestate.payload.error;

import lombok.Getter;

@Getter
public class ErrorDto {

    private final String error;
    private final Boolean success = false;

    private ErrorDto(String error) {
        this.error = error;
    }

    public static ErrorDto errorUnauthorized() {
        return new ErrorDto(ERROR_UNAUTHORIZED);
    }

    public static ErrorDto errorCredentials() {
        return new ErrorDto(ERROR_CREDENTIALS);
    }

    public static ErrorDto errorMinimumTerm() {
        return new ErrorDto(ERROR_MINIMUM_TERM);
    }

    public static ErrorDto errorMissingInfo() {
        return new ErrorDto(ERROR_MISSING_INFO);
    }

    public static ErrorDto errorPropertyUnavailable() {
        return new ErrorDto(ERROR_PROPERTY_UNAVAILABLE);
    }

    public static ErrorDto errorPropertyNotAvailable() {
        return new ErrorDto(ERROR_PROPERTY_NOT_AVAILABLE);
    }

    public static ErrorDto errorPropertyNotFound() {
        return new ErrorDto(ERROR_PROPERTY_NOT_FOUND);
    }


    public static ErrorDto errorBidAmount() {
        return new ErrorDto(ERROR_BID_EXCEEDED);
    }

    public static ErrorDto errorAuctionClosed() {
        return new ErrorDto(ERROR_AUCTION_CLOSED);
    }

    private static final String ERROR_BID_EXCEEDED = "Bid amount must not exceed the established minimum increase.";
    private static final String ERROR_AUCTION_CLOSED = "Auction is closed.";

    private static final String ERROR_PROPERTY_NOT_FOUND = "Property not found.";
    private static final String ERROR_PROPERTY_NOT_AVAILABLE = "Property not available.";

    private static final String ERROR_MINIMUM_TERM = "Minimum mortgage term is 15 years.";
    private static final String ERROR_MISSING_INFO = "Missing financial information. Please update your employment data.";
    private static final String ERROR_PROPERTY_UNAVAILABLE = "Property is unavailable for mortgage.";

    private static final String ERROR_UNAUTHORIZED = "Unauthorised: Full authentication is required to access this resource";
    private static final String ERROR_CREDENTIALS = "Unauthorised: Bad credentials";
}
