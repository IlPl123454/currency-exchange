package org.plenkovii.entity;

import java.math.BigDecimal;

public class ExchangeRate {
    private long ID;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public ExchangeRate(long ID, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.ID = ID;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "ID=" + ID +
                ", baseCurrency=" + baseCurrency.getCode() +
                ", targetCurrency=" + targetCurrency.getCode() +
                ", rate=" + rate +
                '}';
    }
}
