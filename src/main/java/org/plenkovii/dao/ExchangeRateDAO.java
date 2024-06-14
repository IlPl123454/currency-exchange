package org.plenkovii.dao;

import org.plenkovii.entity.Currency;
import org.plenkovii.entity.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {
    CurrencyDAO currencyDAO = new CurrencyDAO();

    public List<ExchangeRate> readAll() throws SQLException, ClassNotFoundException {
//        final String query = "SELECT * FROM ExchangeRates;";
        final String query = "SELECT " +
                "er.id AS id, " +
                "bc.id AS base_id, " +
                "bc.code AS base_code, " +
                "bc.full_name AS base_name, " +
                "bc.sign AS base_sign, " +
                "tc.id AS target_id, " +
                "tc.code AS target_code, " +
                "tc.full_name AS target_name, " +
                "tc.sign AS target_sign," +
                "er.rate AS rate " +
                "FROM ExchangeRates er " +
                "JOIN Currencies bc ON er.base_currency_id = bc.id " +
                "JOIN Currencies tc ON er.target_currency_id = tc.id";

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");

                long baseCurrencyID = rs.getLong("base_id");
                String baseCurrencyCode = rs.getString("base_name");
                String baseCurrencyName = rs.getString("base_code");
                String baseCurrencySign = rs.getString("base_sign");
                Currency baseCurrency = new Currency(baseCurrencyID, baseCurrencyCode, baseCurrencyName, baseCurrencySign);

                long targetCurrencyID = rs.getLong("target_id");
                String targetCurrencyCode = rs.getString("target_code");
                String targetCurrencyName = rs.getString("target_name");
                String targetCurrencySign = rs.getString("target_sign");
                Currency targetCurrency = new Currency(targetCurrencyID, targetCurrencyCode, targetCurrencyName, targetCurrencySign);

                double rate = rs.getDouble("rate");

                exchangeRates.add(new ExchangeRate(id, baseCurrency, targetCurrency, rate));
            }
            return exchangeRates;
        }
    }


}
