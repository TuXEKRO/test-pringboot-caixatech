package com.round3.realestate.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmploymentUtils {

    private static BigDecimal bracket(
        BigDecimal salary,
        int min,
        Integer max,
        BigDecimal ratio
    ) {
        if (salary == null ||
            salary.longValue() == 0L ||
            salary.longValue() <= min) {
            return BigDecimal.ZERO;
        }

        BigDecimal upperLimit = salary;
        if (max != null) {
            upperLimit = salary
                .min(BigDecimal.valueOf(max));
        }

        return BigDecimal.ZERO.add(
            upperLimit
                .subtract(BigDecimal.valueOf(min))
                .add(BigDecimal.ONE)
                .multiply(ratio)
        );
    }

    public static BigDecimal calculateNetMonthly(BigDecimal salary) {
        if (salary == null)
            return null;

        BigDecimal retention = BigDecimal.ZERO
            .add(bracket(salary, 0, 12_450, new BigDecimal("0.19")))
            .add(bracket(salary, 12_450, 20_200, new BigDecimal("0.24")))
            .add(bracket(salary, 20_200, 35_200, new BigDecimal("0.30")))
            .add(bracket(salary, 35_200, 60_000, new BigDecimal("0.37")))
            .add(bracket(salary, 60_000, 300_000, new BigDecimal("0.45")))
            .add(bracket(salary, 300_000, null, new BigDecimal("0.50")));

        return salary
            .subtract(retention)
            .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }
}
