package com.nbonev.converter.areas.currencies.models.binding;

import com.nbonev.converter.areas.currencies.util.Constants;
import com.nbonev.converter.areas.currencies.validation.CurrencyCodeUnique;
import com.nbonev.converter.areas.currencies.validation.CurrencyNameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by Nino Bonev - 28.4.2019 г., 10:14
 */
public class CurrencyEditBindingModel {

    private Long id;

    @NotNull
    @NotEmpty(message = Constants.CURRENCY_NAME_NOT_EMPTY)
    @Size(min = 8, max = 30, message = Constants.CURRENCY_NAME_LENGTH)
    private String currencyName;

    @NotNull
    @NotEmpty(message = Constants.CURRENCY_CODE_NOT_EMPTY)
    @Size(min = 2, max = 6, message = Constants.CURRENCY_CODE_LENGTH)
    private String currencyCode;

    @NotNull(message = Constants.EXCHANGE_RATE_NOT_EMPTY)
    private BigDecimal exchangeRate;

    public CurrencyEditBindingModel() {
    }

    public CurrencyEditBindingModel(String currencyName, String currencyCode, BigDecimal exchangeRate) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
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
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
