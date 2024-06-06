package org.plenkovii.utils;


import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.entity.Currency;


import java.util.ArrayList;
import java.util.List;

public class JsonBuilder {


    public String convertCurrencyDTOToJsonArray(List<CurrencyResponseDTO> currencies) {
        String startBracket = "[\n";
        String finishBracket = "\t\n]";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < currencies.size(); i++) {
            stringBuilder.append('\t');
            stringBuilder.append(this.convertCurrencyDTOToJsonForArray(currencies.get(i)));
            if (i != currencies.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.insert(0, startBracket);
        stringBuilder.append(finishBracket);

        return stringBuilder.toString();
    }

    public String convertCurrencyToJson(CurrencyResponseDTO currency) {
        return "{\n" + "\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n" +
                '}';
    }

    public String convertCurrencyToJson(Currency currency) {
        return "{\n" + "\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n" +
                '}';
    }
    public String convertCurrencyDTOToJsonForArray(CurrencyResponseDTO currency) {
        return "{\n" + "\t\t" +
                "\"id\": " + currency.getId() + ",\n" + "\t\t" +
                "\"code\": \"" + currency.getCode() + "\",\n" + "\t\t" +
                "\"fullName\": \"" + currency.getFullName() + "\",\n" + "\t\t" +
                "\"sign\": \"" + currency.getSign() + "\"\n\t" +
                '}';
    }

    public String buildJsonMessage(String message) {
        return "{\n" + "\t" +
                "\"message\": \"" + message + "\"\n" +
                '}';
    }

    public static void main(String[] args) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        CurrencyResponseDTO currency1 = new CurrencyResponseDTO("RUB", "Ruble", "Р");
        CurrencyResponseDTO currency2 = new CurrencyResponseDTO("KZT", "Tenge", "T");
        List<CurrencyResponseDTO> currencyResponseDTOList = new ArrayList<>();
        currencyResponseDTOList.add(currency1);
        currencyResponseDTOList.add(currency2);

        System.out.println(jsonBuilder.convertCurrencyToJson(new CurrencyResponseDTO("RUUUS", "Ruble Tenge", "123")));

        System.out.println(jsonBuilder.convertCurrencyDTOToJsonArray(currencyResponseDTOList));

        System.out.println(jsonBuilder.buildJsonMessage("Не найдена база данных"));
    }
}
