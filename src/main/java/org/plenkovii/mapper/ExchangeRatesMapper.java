package org.plenkovii.mapper;

import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.ExchangeRate;

public class ExchangeRatesMapper {
    public static ExchangeRateResponseDTO entityToRespDTO(ExchangeRate exchangeRate) {
        return new ExchangeRateResponseDTO(
                exchangeRate.getID(),
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );
    }
}
