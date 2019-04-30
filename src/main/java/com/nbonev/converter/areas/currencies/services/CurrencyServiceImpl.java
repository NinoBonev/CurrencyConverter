package com.nbonev.converter.areas.currencies.services;

import com.nbonev.converter.areas.currencies.entities.Currency;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyCreateBindingModel;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyEditBindingModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyPageViewModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyViewModel;
import com.nbonev.converter.areas.currencies.repositories.CurrencyRepository;
import com.nbonev.converter.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Nino Bonev - 28.4.2019 г., 10:02
 */

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, ModelMapper modelMapper) {
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Currency getCurrencyById(final Long id) {
        Optional<Currency> currency = this.currencyRepository.findById(id);

        if (!currency.isPresent()){
            throw new ResourceNotFoundException();
        }
        return currency.get();
    }

    @Override
    public Currency getCurrencyByName(String currencyName) {
        Optional<Currency> currency = this.currencyRepository.findByCurrencyName(currencyName);

        if (!currency.isPresent()){
            throw new ResourceNotFoundException();
        }
        return currency.get();
    }

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        Optional<Currency> currency = this.currencyRepository.findByCurrencyCode(currencyCode);

        if (!currency.isPresent()){
            throw new ResourceNotFoundException();
        }
        return currency.get();
    }

    @Override
    public List<CurrencyViewModel> getAllCurrencies() {
        List<Currency> currencyList = this.currencyRepository.findAll();
        List<CurrencyViewModel> pageViewModels = new ArrayList<>();

        for (Currency currency : currencyList) {
            pageViewModels.add(this.modelMapper.map(currency, CurrencyViewModel.class));
        }

        pageViewModels = pageViewModels.stream().sorted().collect(Collectors.toList());
        return pageViewModels;
    }

    @Override
    public CurrencyEditBindingModel getCurrencyEditDTOById(Long id) {
        return this.modelMapper.map(this.getCurrencyById(id), CurrencyEditBindingModel.class);
    }

    @Override
    public void saveCurrency(CurrencyCreateBindingModel currencyDTO) {
            Currency currency = this.modelMapper.map(currencyDTO, Currency.class);
            this.currencyRepository.save(currency);
    }

    @Override
    public void deleteCurrency(Long id) {
        this.currencyRepository.deleteById(id);
    }

    @Override
    public void editCurrency(Long id, CurrencyEditBindingModel currencyEditBindingModel) {
        Currency currency = this.getCurrencyById(id);

        currency.setCurrencyCode(currencyEditBindingModel.getCurrencyCode());
        currency.setCurrencyName(currencyEditBindingModel.getCurrencyName());
        currency.setExchangeRate(currencyEditBindingModel.getExchangeRate());

        this.currencyRepository.save(currency);
    }
}
