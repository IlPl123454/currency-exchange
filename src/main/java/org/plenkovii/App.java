package org.plenkovii;

import org.plenkovii.dao.CurrencyDAO;
import org.plenkovii.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        CurrencyDAO dbCrud = new CurrencyDAO();
        List<Currency> currencies = dbCrud.readAll();
        for (Currency currency : currencies) {
            System.out.println(currency);
        }
    }
}
