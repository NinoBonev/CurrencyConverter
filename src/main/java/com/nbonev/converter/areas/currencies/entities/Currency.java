package com.nbonev.converter.areas.currencies.entities;

import com.nbonev.converter.areas.currencies.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Nino Bonev - 28.4.2019 г., 9:40
 */

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = Constants.CURRENCY_NAME_NOT_EMPTY)
    @Size(min = 4, max = 30, message = Constants.CURRENCY_NAME_LENGTH)
    @Column(unique = true)
    private String currencyName;

    @NotNull
    @NotEmpty(message = Constants.CURRENCY_CODE_NOT_EMPTY)
    @Size(min = 2, max = 6, message = Constants.CURRENCY_CODE_LENGTH)
    @Column(unique = true)
    private String currencyCode;

    @NotNull(message = Constants.EXCHANGE_RATE_NOT_EMPTY)
    @Digits(integer=9, fraction=19)
    private BigDecimal exchangeRate;

    public Currency() {
    }

    public Currency(String currencyName, String currencyCode ,BigDecimal exchangeRate) {
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
