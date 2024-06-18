package org.plenkovii.dao;

import org.plenkovii.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyDao extends CrudDao<Currency, Long> {
    Optional<Currency> findByCode(String code) throws SQLException, ClassNotFoundException;
}
