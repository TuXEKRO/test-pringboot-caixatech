package com.round3.realestate.payload.mortgage;

import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
public class MortgageResultApprovedDto {

    private final Boolean approved = true;
    private final Long mortgageId;
    private final BigDecimal netMonthly;
    private final BigDecimal monthlyPayment;
    private final String allowedPercentage;
    private final String message = MESSAGE_APPROVED;
    private final Integer numberOfMonths;

    private MortgageResultApprovedDto(
        Long mortgageId,
        BigDecimal netMonthly,
        BigDecimal monthlyPayment,
        String allowedPercentage,
        Integer numberOfMonths
    ) {
        this.mortgageId = mortgageId;
        this.netMonthly = netMonthly;
        this.monthlyPayment = monthlyPayment;
        this.allowedPercentage = allowedPercentage;
        this.numberOfMonths = numberOfMonths;
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("##.00%");

    public static MortgageResultApprovedDto approved(
        Long mortgageId,
        BigDecimal netMonthly,
        BigDecimal monthlyPayment,
        BigDecimal allowedPercentage,
        Integer numberOfMonths
    ) {
        return new MortgageResultApprovedDto(
            mortgageId,
            netMonthly,
            monthlyPayment,
            FORMAT.format(allowedPercentage),
            numberOfMonths
        );
    }

    private static final String MESSAGE_APPROVED = "Mortgage approved.";
}
