package com.round3.realestate.util;

import java.math.BigDecimal;

public class StringUtils {
    public static BigDecimal parseBigDecimalOrNull(String string) {
        if (string != null)
            try {
                return new BigDecimal(
                    string.trim()
                        .replaceAll("\\.", "")
                        .replaceAll(",", "")
                        .replaceAll(" ", "")
                );
            } catch (Exception ignored) {
            }
        return null;
    }
}
