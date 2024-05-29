package org.plenkovii.service;

import org.plenkovii.dao.CurrencyDAO;
import org.plenkovii.dto.CurrencyDTO;
import org.plenkovii.entity.Currency;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyService {
    public List<CurrencyDTO> getAllCurrencies() throws ClassNotFoundException, SQLException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        List<CurrencyDTO> result = new ArrayList<>();

        List<Currency> currencies = currencyDAO.readAll();
        for (Currency currency : currencies) {
            result.add(new CurrencyDTO(currency.getCode(), currency.getFullName(), currency.getSign()));
        }

        return result;
    }

    public CurrencyDTO getCurrencyByCode(String code) throws ClassNotFoundException, SQLException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        Optional<Currency> optionalCurrency = currencyDAO.findByCode(code);

        Currency currency = optionalCurrency.orElseThrow(() -> new IllegalArgumentException());

        return new CurrencyDTO(currency.getCode(), currency.getFullName(), currency.getSign());
    }
}
