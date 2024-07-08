package org.plenkovii.utils;


import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.dto.ExchangeResponseDTO;
import org.plenkovii.entity.Currency;

import java.util.List;

public class JsonBuilder {
    public static String convertCurrencyDTOToJsonArray(List<CurrencyResponseDTO> currencies) {
        String startBracket = "[\n";
        String finishBracket = "\t\n]";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < currencies.size(); i++) {
            stringBuilder.append('\t');
            stringBuilder.append(convertCurrencyDTOToJsonForArray(currencies.get(i)));
            if (i != currencies.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.insert(0, startBracket);
        stringBuilder.append(finishBracket);

        return stringBuilder.toString();
    }

    public static String convertCurrencyToJson(CurrencyResponseDTO currency) {
        return "{\n" + "\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n" +
                '}';
    }

    public static String convertCurrencyToJson(Currency currency) {
        return "{\n" + "\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n" +
                '}';
    }

    private static String convertCurrencyToJsonForExchangeRate(Currency currency) {
        return "{\n" + "\t\t\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t\t\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t\t\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t\t\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n\t\t" +
                '}';
    }

    private static String convertCurrencyToJsonForExchangeRateArray(Currency currency) {
        return "{\n" + "\t\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t\t\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t\t\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t\t\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n\t" +
                '}';
    }

    public static String convertCurrencyDTOToJsonForArray(CurrencyResponseDTO currency) {
        return "{\n" + "\t\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n\t" +
                '}';
    }

    public static String convertExchangeRatesToJsonArray(List<ExchangeRateResponseDTO> exchangeRates) {
        String startBracket = "[\n";
        String finishBracket = "\t\t\n]";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < exchangeRates.size(); i++) {
            stringBuilder.append('\t');
            stringBuilder.append(convertExchangeRateToJsonForArray(exchangeRates.get(i)));
            if (i != exchangeRates.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.insert(0, startBracket);
        stringBuilder.append(finishBracket);

        return stringBuilder.toString();
    }

    public static String buildJsonMessage(String message) {
        return "{\n" + "\t" +
                "\"message\": \"" + message + "\"\n" +
                '}';
    }

    public static String convertExchangeRateToJson(ExchangeRateResponseDTO exchangeRate) {
        return "{\n" + "\t" +
                "\"id\": " + exchangeRate.getID() + ",\n" + "\t" +
                "\"baseCurrency\": " + convertCurrencyToJsonForExchangeRateArray(exchangeRate.getBaseCurrency()) + ",\n" + "\t" +
                "\"TargetCurrency\": " + convertCurrencyToJsonForExchangeRateArray(exchangeRate.getTargetCurrency()) + ",\n" + "\t" +
                "\"rate\": \"" + exchangeRate.getRate() + "\"\n" +
                '}';
    }


    private static String convertExchangeRateToJsonForArray(ExchangeRateResponseDTO exchangeRate) {
        return "{\n" + "\t\t" +
                "\"id\": " + exchangeRate.getID() + ",\n" + "\t\t" +
                "\"baseCurrency\": " + convertCurrencyToJsonForExchangeRate(exchangeRate.getBaseCurrency()) + ",\n" + "\t\t" +
                "\"TargetCurrency\": " + convertCurrencyToJsonForExchangeRate(exchangeRate.getTargetCurrency()) + ",\n" + "\t\t" +
                "\"rate\": \"" + exchangeRate.getRate() + "\"\n\t" +
                '}';
    }

    public static String convertExchangeToJson(ExchangeResponseDTO exchangeRate) {
        return "{\n" + "\t" +
                "\"baseCurrency\": " + convertCurrencyToJsonForExchangeRateArray(exchangeRate.getBaseCurrency()) + ",\n" + "\t" +
                "\"TargetCurrency\": " + convertCurrencyToJsonForExchangeRateArray(exchangeRate.getTargetCurrency()) + ",\n" + "\t" +
                "\"rate\": \"" + exchangeRate.getRate() + "\"\n" +
                "\"amount\": \"" + exchangeRate.getAmount() + "\"\n" +
                "\"convertedAmount\": \"" + exchangeRate.getConvertedAmount() + "\"\n" +
                '}';
    }


}
