package org.plenkovii.service;

import org.plenkovii.dao.CurrencyDAO;
import org.plenkovii.dto.CurrencyRequestDTO;
import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.entity.Currency;
import org.plenkovii.mapper.CurrencyMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyService {
    CurrencyDAO currencyDAO = new CurrencyDAO();

    public List<CurrencyResponseDTO> getAllCurrencies() throws ClassNotFoundException, SQLException {
        List<CurrencyResponseDTO> result = new ArrayList<>();

        List<Currency> currencies = currencyDAO.readAll();

        for (Currency currency : currencies) {
            result.add(CurrencyMapper.entityToRespDTO(currency));
        }

        return result;
    }

    public CurrencyResponseDTO getCurrencyByCode(String code) throws ClassNotFoundException, SQLException {
        Optional<Currency> optionalCurrency = currencyDAO.findByCode(code);

        Currency currency = optionalCurrency.orElseThrow(() -> new IllegalArgumentException());

        return new CurrencyResponseDTO(currency.getCode(), currency.getFullName(), currency.getSign());
    }

    public Currency createCurrency(CurrencyRequestDTO currencyRequestDTO) throws SQLException, ClassNotFoundException {
        return currencyDAO.create(CurrencyMapper.reqDTOToEntity(currencyRequestDTO));
    }
}
