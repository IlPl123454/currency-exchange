package org.plenkovii.service;

import org.plenkovii.dao.CurrencyDao;
import org.plenkovii.dao.JdbcCurrencyDAO;
import org.plenkovii.dto.CurrencyRequestDTO;
import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.entity.Currency;
import org.plenkovii.mapper.CurrencyMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyService {
    CurrencyDao CurrencyDAO = new JdbcCurrencyDAO();

    public List<CurrencyResponseDTO> getAllCurrencies() throws ClassNotFoundException, SQLException {
        List<CurrencyResponseDTO> result = new ArrayList<>();

        List<Currency> currencies = CurrencyDAO.findAll();

        for (Currency currency : currencies) {
            result.add(CurrencyMapper.entityToRespDTO(currency));
        }

        return result;
    }

    public CurrencyResponseDTO getCurrencyByCode(String code) throws ClassNotFoundException, SQLException {
        Optional<Currency> optionalCurrency = CurrencyDAO.findByCode(code);

        Currency currency = optionalCurrency.orElseThrow(() -> new IllegalArgumentException());

        return new CurrencyResponseDTO(currency.getCode(), currency.getFullName(), currency.getSign());
    }

    public Currency createCurrency(CurrencyRequestDTO currencyRequestDTO) throws SQLException, ClassNotFoundException {
        return CurrencyDAO.save(CurrencyMapper.reqDTOToEntity(currencyRequestDTO));
    }
}
