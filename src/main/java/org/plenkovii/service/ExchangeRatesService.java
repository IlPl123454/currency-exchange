package org.plenkovii.service;

import org.plenkovii.dao.JdbcCurrencyDAO;
import org.plenkovii.dao.JdbcExchangeRateDAO;
import org.plenkovii.dto.ExchangeRateRequestDTO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.Currency;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.mapper.ExchangeRatesMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesService {
    JdbcExchangeRateDAO jdbcExchangeRateDAO = new JdbcExchangeRateDAO();
    JdbcCurrencyDAO jdbcCurrencyDAO = new JdbcCurrencyDAO();

    public List<ExchangeRateResponseDTO> getAllExchangeRates() throws SQLException, ClassNotFoundException {
        List<ExchangeRateResponseDTO> result = new ArrayList<>();

        List<ExchangeRate> exchangeRates = jdbcExchangeRateDAO.findAll();

        for (ExchangeRate exchangeRate : exchangeRates) {
            result.add(ExchangeRatesMapper.entityToRespDTO(exchangeRate));
        }
        return result;
    }

    public Optional<ExchangeRate> getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException, ClassNotFoundException {
        Optional<ExchangeRate> result = jdbcExchangeRateDAO.findByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
        if (result.isPresent()) {
            return result;
        } else {
            throw new EntityExistException("Обменный курс для пары не найден");
        }
    }

    public ExchangeRate saveExchangeRate(ExchangeRateRequestDTO exchangeRateRequestDTO) throws SQLException, ClassNotFoundException {
        Optional<Currency> baseCurrencyOpt = jdbcCurrencyDAO.findByCode(exchangeRateRequestDTO.getBaseCurrencyCode());
        if (baseCurrencyOpt.isEmpty()) {
            throw new EntityExistException("Валюта с кодом " + exchangeRateRequestDTO.getBaseCurrencyCode() + " не найдена.");
        }

        Optional<Currency> targetCurrencyOpt = jdbcCurrencyDAO.findByCode(exchangeRateRequestDTO.getTargetCurrencyCode());
        if (targetCurrencyOpt.isEmpty()) {
            throw new EntityExistException("Валюта с кодом " + exchangeRateRequestDTO.getTargetCurrencyCode() + " не найдена.");
        }

        ExchangeRate exchangeRate = ExchangeRatesMapper.ReqDTOtoEntity(exchangeRateRequestDTO, baseCurrencyOpt.get(), targetCurrencyOpt.get());

        jdbcExchangeRateDAO.save(exchangeRate);

        return exchangeRate;
    }
}
