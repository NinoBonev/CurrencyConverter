package com.nbonev.converter.areas.currencies.services;

import com.nbonev.converter.areas.currencies.entities.Currency;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyCreateBindingModel;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyEditBindingModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyPageViewModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyViewModel;

import java.util.List;

/**
 * Created by Nino Bonev - 28.4.2019 г., 9:58
 */
public interface CurrencyService {

    Currency getCurrencyById(Long id);

    Currency getCurrencyByName(String currencyName);

    Currency getCurrencyByCode(String currencyCode);

    List<CurrencyViewModel> getAllCurrencies();

    CurrencyEditBindingModel getCurrencyEditDTOById(Long id);

    void saveCurrency(CurrencyCreateBindingModel currencyDTO);

    void deleteCurrency(Long id);

    void editCurrency(Long id, CurrencyEditBindingModel currencyEditBindingModel);

}
