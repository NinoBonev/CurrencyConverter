package com.nbonev.converter.areas.currencies.controllers;

import com.nbonev.converter.areas.currencies.models.binding.CurrencyCreateBindingModel;
import com.nbonev.converter.areas.currencies.models.binding.CurrencyEditBindingModel;
import com.nbonev.converter.areas.currencies.services.CurrencyService;
import com.nbonev.converter.areas.currencies.util.Constants;
import com.nbonev.converter.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;


/**
 * Created by Nino Bonev - 28.4.2019 г., 10:04
 */

@Controller
@RequestMapping("/currencies")
public class CurrencyController extends BaseController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/all")
    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    public ModelAndView all() {
        return super.view("currencies/all",
                "currencyPageViewModel",
                this.currencyService.getAllCurrencies());
    }

    @GetMapping(path = "/add")
    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    public ModelAndView register(@ModelAttribute("currencyAddModel")
                                         CurrencyCreateBindingModel currencyCreateBindingModel) {
        return super.view("currencies/add",
                "currencyAddModel", currencyCreateBindingModel);
    }


    @PostMapping(path = "/add")
    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    public ModelAndView register(@Valid @ModelAttribute("currencyAddModel")
                                         CurrencyCreateBindingModel currencyCreateBindingModel,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.register(currencyCreateBindingModel);
        }

        this.currencyService.saveCurrency(currencyCreateBindingModel);
        return super.redirect("/currencies/all");
    }

    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return super.view("currencies/edit",
                "currencyEditModel", this.currencyService.getCurrencyEditDTOById(id));
    }

    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    @PostMapping(path = "/edit/{id}")
    public ModelAndView edit(@Valid @ModelAttribute("currencyEditModel") CurrencyEditBindingModel currencyEditBindingModel,
                             BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.rejectValue("exchangeRate", "error.user", Constants.INCORRECT_EXCHANGE_RATE);
            bindingResult.rejectValue("currencyCode", "error.user", Constants.INCORRECT_CURRENCY_CODE);
            bindingResult.rejectValue("currencyName", "error.user", Constants.INCORRECT_CURRENCY_NAME);
            return this.edit(id);
        }

        this.currencyService.editCurrency(id, currencyEditBindingModel);
        return super.redirect("/currencies/all");
    }

    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") Long id) {
        this.currencyService.deleteCurrency(id);
        return super.redirect("/currencies/all");
    }

    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    @RequestMapping("/synchronize/{id}")
    public ModelAndView synchronize(@PathVariable(name = "id") Long id) throws IOException {
        this.currencyService.synchronizeCurrency(id);
        return super.redirect("/currencies/all");
    }

    @PreAuthorize("@accessService.isInRoleAdmin(authentication)")
    @RequestMapping("/synchronize/all")
    public ModelAndView synchronizeAll() throws IOException {
        this.currencyService.synchronizeAllCurrencies();
        return super.redirect("/currencies/all");
    }

}
