package org.plenkovii.dao;

import org.plenkovii.entity.ExchangeRate;

import java.sql.SQLException;
import java.util.Optional;

public interface ExchangeRateDAO extends CrudDao<ExchangeRate,Long> {
    Optional<ExchangeRate> findByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException, ClassNotFoundException;
}
