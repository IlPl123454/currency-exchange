package org.plenkovii.utils;

import org.plenkovii.exception.InvalidParameterexception;

import java.math.BigDecimal;

public class ExchangeValidator {
    public static void validate(String baseCurrencyCode, String targetCurrencyCode, String amount) {
        if (!isCodeCorrect(baseCurrencyCode) || !isCodeCorrect(targetCurrencyCode)) {
            throw new InvalidParameterexception("Некорректно указан код валюты");
        } else if (!isAmountCorrect(amount)) {
            throw new InvalidParameterexception("Некорректно указано колючество валюты для перевода");
        }
    }

    private static boolean isCodeCorrect(String code) {
        return code != null && code.length() == 3 && !code.isBlank();
    }

    private static boolean isAmountCorrect(String amount) {
        try {
            new BigDecimal(amount);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
