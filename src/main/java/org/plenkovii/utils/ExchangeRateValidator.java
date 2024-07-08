package org.plenkovii.utils;

import org.plenkovii.exception.InvalidParameterexception;

public class ExchangeRateValidator {
    public static void validate(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        if (!isCodeCorrect(baseCurrencyCode)) {
            throw new InvalidParameterexception("Неверно указан код первой валюты");
        } else if (!isCodeCorrect(targetCurrencyCode)) {
            throw new InvalidParameterexception("Неверно указан код второй валюты");
        } else if (!isRateCorrect(rate)) {
            throw new InvalidParameterexception("Неверно указан курс обмена для валют");
        }
    }

    private static boolean isCodeCorrect(String code) {
        return code != null && code.length() == 3 && !code.isBlank();
    }

    private static boolean isRateCorrect(String rateS) {
        double rateD;
        try {
            rateD = Double.parseDouble(rateS);
            return true;
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
    }
}
