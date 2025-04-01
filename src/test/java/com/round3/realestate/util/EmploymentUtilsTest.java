package com.round3.realestate.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmploymentUtilsTest {
    @Test
    public void testCalculateNetMonthly() {
        var resultMillion = EmploymentUtils.calculateNetMonthly(
            BigDecimal.valueOf(1_000_000)
        );

        assertEquals(
            43674.7,
            resultMillion.doubleValue(),
            0.03
        );

        var resultFifty = EmploymentUtils.calculateNetMonthly(
            BigDecimal.valueOf(50_000)
        );

        assertEquals(
            2983.09,
            resultFifty.doubleValue(),
            0.03
        );
    }
}
