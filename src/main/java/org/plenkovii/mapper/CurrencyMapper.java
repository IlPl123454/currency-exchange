package org.plenkovii.mapper;

import org.plenkovii.dto.CurrencyRequestDTO;
import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.entity.Currency;

public class CurrencyMapper {
    public static CurrencyResponseDTO entityToRespDTO(Currency currency) {
        return new CurrencyResponseDTO(
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }

    public static Currency reqDTOToEntity(CurrencyRequestDTO currencyRequestDTO) {
        return new Currency(
                currencyRequestDTO.getCode(),
                currencyRequestDTO.getFullName(),
                currencyRequestDTO.getSign()
        );
    }
}
