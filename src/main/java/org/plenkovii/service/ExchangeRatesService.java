package org.plenkovii.service;

import org.plenkovii.dao.JdbcExchangeRateDAO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.mapper.ExchangeRatesMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesService {
    JdbcExchangeRateDAO jdbcExchangeRateDAO = new JdbcExchangeRateDAO();

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
}
