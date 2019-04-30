package com.nbonev.converter.areas.currencies.services;

import com.nbonev.converter.areas.currencies.entities.Currency;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyCreateBindingModel;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyEditBindingModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyPageViewModel;
import com.nbonev.converter.areas.currencies.models.view.CurrencyViewModel;
import com.nbonev.converter.areas.currencies.repositories.CurrencyRepository;
import com.nbonev.converter.areas.currencies.util.Constants;
import com.nbonev.converter.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
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
        Currency currency = this.currencyRepository.findCurrencyByCurrencyName(currencyName);

        if (currency == null){
            throw new ResourceNotFoundException();
        }
        return currency;
    }

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        Currency currency = this.currencyRepository.findCurrencyByCurrencyCode(currencyCode);

        if (currency == null){
            throw new ResourceNotFoundException();
        }
        return currency;
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

    @Override
    public void synchronizeCurrency(Long id) throws IOException {

        Currency currency = this.getCurrencyById(id);

        Document doc = Jsoup.connect(Constants.BNB_URL).get();
        Elements elements = doc.select("td");

        for (Element element : elements) {
            this.updateCurrency(element, currency);
        }
    }

    @Override
    public void synchronizeAllCurrencies() throws IOException {
        List<Currency> currencies = this.currencyRepository.findAll();

        Document doc = Jsoup.connect(Constants.BNB_URL).get();
        Elements elements = doc.select("td");

        for (Element element : elements) {
            for (Currency currency : currencies) {
                this.updateCurrency(element, currency);
            }
        }
    }

    private void updateCurrency(Element element, Currency currency){
        if (element.text().equals(currency.getCurrencyCode())){
            Integer divide = Integer.parseInt(element.parent().child(2).text());
            Double rate = Double.parseDouble(element.parent().child(3).text());

            currency.setExchangeRate(BigDecimal.valueOf(rate / divide));
            this.currencyRepository.save(currency);
        }
    }
}
