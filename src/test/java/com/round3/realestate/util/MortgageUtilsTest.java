package com.round3.realestate.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortgageUtilsTest {
    @Test
    public void testCalculateMonthlyPayment() {
        var totalCost = BigDecimal
            .valueOf(680_000)
            .multiply(new BigDecimal("1.15"));

        BigDecimal result = MortgageUtils.calculateMonthlyPayment(
            totalCost,
            20*12
        );

        assertEquals(
            3956.01,
            result.doubleValue(),
            0.01
        );
    }
}