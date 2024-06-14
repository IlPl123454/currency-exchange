package org.plenkovii.dto;

import org.plenkovii.entity.Currency;

public class ExchangeRateResponseDTO {
    long ID;
    Currency baseCurrency;
    Currency targetCurrency;
    double rate;

    public ExchangeRateResponseDTO(long ID, Currency baseCurrency, Currency targetCurrency, double rate) {
        this.ID = ID;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
