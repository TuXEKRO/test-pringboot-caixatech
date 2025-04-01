package com.round3.realestate.payload.mortgage;

import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
public class MortgageResultRejectedDto {

    private final Boolean approved = false;
    private final BigDecimal netMonthly;
    private final BigDecimal monthlyPayment;
    private final String allowedPercentage;
    private final String message = MESSAGE_REJECTED;

    private MortgageResultRejectedDto(
        BigDecimal netMonthly,
        BigDecimal monthlyPayment,
        String allowedPercentage
    ) {
        this.netMonthly = netMonthly;
        this.monthlyPayment = monthlyPayment;
        this.allowedPercentage = allowedPercentage;
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("##.00%");

    public static MortgageResultRejectedDto rejected(
        BigDecimal netMonthly,
        BigDecimal monthlyPayment,
        BigDecimal allowedPercentage
    ) {
        return new MortgageResultRejectedDto(
            netMonthly,
            monthlyPayment,
            FORMAT.format(allowedPercentage)
        );
    }

    private static final String MESSAGE_REJECTED = "Mortgage rejected. The monthly payment exceeds the allowed percentage of your net income.";
}
