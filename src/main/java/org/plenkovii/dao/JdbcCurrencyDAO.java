package org.plenkovii.dao;

import org.plenkovii.entity.Currency;
import org.plenkovii.exception.EntityExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCurrencyDAO implements CurrencyDao {

    @Override
    public List<Currency> findAll() throws ClassNotFoundException, SQLException {
        final String query = "SELECT * FROM Currencies";
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
    }

    @Override
    public Currency save(Currency currency) throws SQLException, ClassNotFoundException {
        final String query = "INSERT INTO Currencies(code, full_name, sign) VALUES (?, ?, ?) RETURNING *";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            System.out.println("подключились к БД");

            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                    throw new EntityExistException();
                }
            }

            System.out.println("вставили в БД");

            int id = resultSet.getInt("id");
            String code = resultSet.getString("code");
            String fullName = resultSet.getString("full_name");
            String sign = resultSet.getString("sign");
            System.out.println("закончили");

            return new Currency(id, code, fullName, sign);
        }
    }

    @Override
    public Optional<Currency> update(Currency currency) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Currency> findByCode(String currencyCode) throws SQLException, ClassNotFoundException {
        final String query = "SELECT * FROM Currencies WHERE code = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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

    @Override
    public Optional<Currency> findById(Long currencyId) throws SQLException, ClassNotFoundException {
        final String query = "SELECT * FROM Currencies WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, currencyId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String fullName = rs.getString("full_name");
                String sign = rs.getString("sign");

                Currency currency = new Currency(id, code, fullName, sign);
                return Optional.of(currency);
            } else {
                return Optional.empty();
            }
        }
    }
}
