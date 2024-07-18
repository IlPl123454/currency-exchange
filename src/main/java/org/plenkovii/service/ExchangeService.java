package org.plenkovii.service;

import org.plenkovii.dao.JdbcExchangeRateDAO;
import org.plenkovii.dto.ExchangeRequestDTO;
import org.plenkovii.dto.ExchangeResponseDTO;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.exception.EntityExistException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Optional;

public class ExchangeService {
    JdbcExchangeRateDAO exchangeRateDAO = new JdbcExchangeRateDAO();

    public ExchangeResponseDTO exchange(ExchangeRequestDTO exchangeRequestDTO) throws SQLException, ClassNotFoundException {
        Optional<ExchangeRate> exchangeRateOpt = getExchangeRateByCurrencyCodes(
                exchangeRequestDTO.getBaseCurrencyCode(),
                exchangeRequestDTO.getTargetCurrencyCode()
        );

        if (exchangeRateOpt.isEmpty()) {
            throw new EntityExistException("Обменный курс для данных валют не найден.");
        }

        ExchangeRate exchangeRate = exchangeRateOpt.get();
        BigDecimal rate = exchangeRate.getRate();
        BigDecimal amount = BigDecimal.valueOf(exchangeRequestDTO.getAmount());
        BigDecimal convertedAmount = rate.multiply(amount).setScale(2, RoundingMode.HALF_UP);

        return new ExchangeResponseDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                rate,
                amount,
                convertedAmount
        );
    }


    private Optional<ExchangeRate> getExchangeRateByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException, ClassNotFoundException {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateDAO.findByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
        if (exchangeRateOptional.isPresent()) {
            return exchangeRateOptional;
        }

        exchangeRateOptional = exchangeRateDAO.findByCurrencyCodes(targetCurrencyCode, baseCurrencyCode);
        if (exchangeRateOptional.isPresent()) {
            return Optional.of(new ExchangeRate(
                    exchangeRateOptional.get().getTargetCurrency(),
                    exchangeRateOptional.get().getBaseCurrency(),
                    BigDecimal.ONE.divide(exchangeRateOptional.get().getRate(), 6, RoundingMode.HALF_EVEN)
            ));
        }

        Optional<ExchangeRate> usdBaseCurrencyExchangeRate = exchangeRateDAO.findByCurrencyCodes("USD", baseCurrencyCode);
        Optional<ExchangeRate> usdTargetCurrencyExchangeRate = exchangeRateDAO.findByCurrencyCodes("USD", targetCurrencyCode);
        if (usdBaseCurrencyExchangeRate.isPresent() && usdTargetCurrencyExchangeRate.isPresent()) {
            return Optional.of(new ExchangeRate(
                    usdBaseCurrencyExchangeRate.get().getTargetCurrency(),
                    usdTargetCurrencyExchangeRate.get().getTargetCurrency(),
                    getRateByUSDRate(usdBaseCurrencyExchangeRate.get(), usdTargetCurrencyExchangeRate.get())));
        }
        return Optional.empty();
    }

    private BigDecimal getRateByUSDRate(ExchangeRate usdBaseCurrencyExchangeRate, ExchangeRate usdTargetCurrencyExchangeRate) {
        BigDecimal usdBaseRate = usdBaseCurrencyExchangeRate.getRate();
        BigDecimal usdTargetRate = usdTargetCurrencyExchangeRate.getRate();
        return usdTargetRate.divide(usdBaseRate, 6, RoundingMode.HALF_EVEN);
    }
}
