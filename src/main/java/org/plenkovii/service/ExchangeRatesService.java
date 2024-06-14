package org.plenkovii.service;

import org.plenkovii.dao.ExchangeRateDAO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.mapper.ExchangeRatesMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesService {
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();

    public List<ExchangeRateResponseDTO> getAllExchangeRates() throws SQLException, ClassNotFoundException {
        List<ExchangeRateResponseDTO> result = new ArrayList<>();

        List<ExchangeRate> exchangeRates = exchangeRateDAO.readAll();

        for (ExchangeRate exchangeRate : exchangeRates) {
            result.add(ExchangeRatesMapper.entityToRespDTO(exchangeRate));
        }

        return result;
    }
}
