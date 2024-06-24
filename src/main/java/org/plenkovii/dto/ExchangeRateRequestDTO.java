package org.plenkovii.dto;

public class ExchangeRateRequestDTO {
    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final double rate;

    public ExchangeRateRequestDTO(String baseCurrencyCode, String targetCurrencyCode, double rate) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.rate = rate;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public double getRate() {
        return rate;
    }
}

