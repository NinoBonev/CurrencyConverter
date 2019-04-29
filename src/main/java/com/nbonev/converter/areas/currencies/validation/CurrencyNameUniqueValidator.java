package com.nbonev.converter.areas.currencies.validation;

import com.nbonev.converter.areas.currencies.services.CurrencyService;
import com.nbonev.converter.areas.currencies.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CurrencyNameUniqueValidator implements ConstraintValidator<CurrencyNameUnique, String> {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyNameUniqueValidator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void initialize(CurrencyNameUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String currencyName, ConstraintValidatorContext context) {
        if (currencyName == null) {
            return false;
        }
        if (currencyService.getCurrencyByName(currencyName) != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Constants.CURRENCY_TAKEN)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}