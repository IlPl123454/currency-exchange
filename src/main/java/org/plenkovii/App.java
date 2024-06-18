package org.plenkovii;

import org.plenkovii.dao.JdbcCurrencyDAO;
import org.plenkovii.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        JdbcCurrencyDAO dbCrud = new JdbcCurrencyDAO();
        List<Currency> currencies = dbCrud.findAll();
        for (Currency currency : currencies) {
            System.out.println(currency);
        }
    }
}
