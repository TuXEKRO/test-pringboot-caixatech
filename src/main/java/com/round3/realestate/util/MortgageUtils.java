package com.round3.realestate.util;

import com.round3.realestate.entity.EmploymentContract;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MortgageUtils {

    private static final BigDecimal INDEFINITE_PERCENTAGE = new BigDecimal("0.3");
    private static final BigDecimal TEMPORARY_PERCENTAGE = new BigDecimal("0.15");
    private static final BigDecimal MONTHLY_INTEREST = BigDecimal.valueOf(0.02 / 12.0);

    public static BigDecimal calculateMonthlyPayment(
        BigDecimal totalCost,
        int numberOfMonth
    ) {
        BigDecimal numerator = totalCost
            .multiply(MONTHLY_INTEREST);

        BigDecimal denominator = BigDecimal.ONE.subtract(
            BigDecimal.ONE.add(MONTHLY_INTEREST).
                pow(-numberOfMonth, MathContext.DECIMAL64)
        );

        return numerator
            .divide(denominator, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getAllowedPercentage(EmploymentContract contract) {
        if (contract == EmploymentContract.indefinite) {
            return INDEFINITE_PERCENTAGE;
        } else {
            return TEMPORARY_PERCENTAGE;
        }
    }

    public static BigDecimal getPaymentThreshold(
        BigDecimal netMonthly,
        BigDecimal allowedPercentage
    ) {
        return netMonthly.multiply(allowedPercentage);
    }

    public static int getNumberOfMonth(int years) {
        return years * 12;
    }
}
