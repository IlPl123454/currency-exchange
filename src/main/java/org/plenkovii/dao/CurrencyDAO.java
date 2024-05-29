package org.plenkovii.dao;

import org.plenkovii.DbConnection;
import org.plenkovii.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements CrudDao<Currency> {

    @Override
    public List<Currency> readAll() throws ClassNotFoundException, SQLException {
        List<Currency> currencies = new ArrayList<>();
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Currencies");
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String code = rs.getString("code");
            String fullName = rs.getString("full_name");
            String sign = rs.getString("sign");

            currencies.add(new Currency(id, code, fullName, sign));
        }

        return currencies;
    }

    @Override
    public void create(Currency currency) throws ClassNotFoundException, SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Currencies(code, full_name, sign) VALUES (?, ?, ?)");

        preparedStatement.setString(1, currency.getCode());
        preparedStatement.setString(2, currency.getFullName());
        preparedStatement.setString(3, currency.getSign());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    public Optional<Currency> findByCode(String currencyCode) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Currencies WHERE code = ?");
        preparedStatement.setString(1, currencyCode);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String code = rs.getString("code");
            String fullName = rs.getString("full_name");
            String sign = rs.getString("sign");

            Currency currency = new Currency(id, code, fullName, sign);
            return Optional.of(currency);
        }
        return Optional.empty();
    }
}
