package com.nbonev.converter.areas.currencies.models.view;

import org.springframework.data.domain.Page;

/**
 * Created by Nino Bonev - 29.4.2019 г., 9:16
 */
public class CurrencyPageViewModel {
    private Page<CurrencyViewModel> currencies;

    public Page<CurrencyViewModel> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Page<CurrencyViewModel> currencies) {
        this.currencies = currencies;
    }
}
