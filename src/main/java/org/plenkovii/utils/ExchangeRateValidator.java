package org.plenkovii.utils;

import org.plenkovii.exception.InvalidParameterexception;

import java.math.BigDecimal;

public class ExchangeRateValidator {
    public static void validate(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        if (!isCodeCorrect(baseCurrencyCode)) {
            throw new InvalidParameterexception("Неверно указан код первой валюты");
        } else if (!isCodeCorrect(targetCurrencyCode)) {
            throw new InvalidParameterexception("Неверно указан код второй валюты");
        }
//        else if (!isRateCorrect(rate)) {
//            throw new InvalidParameterexception("Неверно указан курс обмена для валют");
//        }

        try {
            new BigDecimal(rate);
        } catch (NumberFormatException e) {
            throw new InvalidParameterexception("Вы ввели не число");
        } catch (NullPointerException e) {
            throw new InvalidParameterexception("Пустота...");
        }


    }

    private static boolean isCodeCorrect(String code) {
        return code != null && code.length() == 3 && !code.isBlank();
    }

    private static boolean isRateCorrect(String rateS) {
        BigDecimal rateD;
        try {
            rateD = new BigDecimal(rateS);
            return true;
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
