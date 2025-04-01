package com.round3.realestate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MortgageRejectedException extends RuntimeException {
    private final BigDecimal netMonthly;
    private final BigDecimal monthlyPayment;
    private final BigDecimal allowedPercentage;
}