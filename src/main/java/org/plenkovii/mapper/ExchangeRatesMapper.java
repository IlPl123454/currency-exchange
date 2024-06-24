package org.plenkovii.mapper;

import org.plenkovii.dto.ExchangeRateRequestDTO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.Currency;
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

    public static ExchangeRate ReqDTOtoEntity(ExchangeRateRequestDTO exchangeRateRequestDTO, Currency baseCurrency, Currency targetCurrency) {
        return new ExchangeRate(baseCurrency, targetCurrency, exchangeRateRequestDTO.getRate());
    }
}
