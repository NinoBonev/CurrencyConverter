package com.nbonev.converter.areas.home.controllers;

import com.nbonev.converter.areas.currencies.models.view.CurrencyViewModel;
import com.nbonev.converter.areas.currencies.services.CurrencyService;
import com.nbonev.converter.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController {

    private final CurrencyService currencyService;

    @Autowired
    public HomeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        List<CurrencyViewModel> currencies = this.currencyService.getAllCurrencies();
        return super.view("index", "currencies", currencies);
    }
}
