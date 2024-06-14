package org.plenkovii.utils;

import org.plenkovii.dto.CurrencyRequestDTO;
import org.plenkovii.exception.InvalidParameterexception;

public class CurrencyValidator {
    public static void currencyRequestValidation(CurrencyRequestDTO currencyRequestDTO) {
        if (!isCodeCorrect(currencyRequestDTO.getCode())) {
            throw new InvalidParameterexception("Код валюты введен некорректно, он должен состоять из 3 символов");
        } else if (!isFullNameCorrect(currencyRequestDTO.getFullName())) {
            throw new InvalidParameterexception("Полное имя валюты введено некорректно");
        } else if (!isSignCorrect(currencyRequestDTO.getSign())) {
            throw new InvalidParameterexception("Символ валюты введен некорректно, он должен состоять из одного символа");
        }
    }
    private static boolean isCodeCorrect(String code) {
        return code != null && code.length() == 3 && !code.isBlank();
    }

    private static boolean isFullNameCorrect(String fullName) {
        return fullName != null && !fullName.isBlank();
    }

    private static boolean isSignCorrect(String sign) {
        return sign != null && sign.length() == 1 && !sign.isBlank();
    }
}
