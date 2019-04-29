package com.nbonev.converter.areas.currencies.models.view;

import com.nbonev.converter.areas.currencies.util.Constants;

import java.math.BigDecimal;

/**
 * Created by Nino Bonev - 29.4.2019 г., 9:10
 */
public class CurrencyViewModel implements Comparable<CurrencyViewModel> {

    private Long id;
    private String currencyName;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private String exchangedCurrencyCode;

    public CurrencyViewModel() {
    }

    public CurrencyViewModel(String currencyName, String currencyCode,
                             BigDecimal exchangeRate) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.exchangedCurrencyCode = Constants.CURRENT_EXCHANGE_CURRENCY_CODE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate.stripTrailingZeros();
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getExchangedCurrencyCode() {
        return exchangedCurrencyCode;
    }

    public void setExchangedCurrencyCode(String exchangedCurrencyCode) {
        this.exchangedCurrencyCode = exchangedCurrencyCode;
    }

    @Override
    public int compareTo(CurrencyViewModel o) {
        return this.getCurrencyName().compareTo(o.getCurrencyName());
    }
}
