package com.nbonev.converter.areas.currencies.validation;

import com.nbonev.converter.areas.currencies.services.CurrencyService;
import com.nbonev.converter.areas.currencies.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CurrencyCodeUniqueValidator implements ConstraintValidator<CurrencyCodeUnique, String> {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyCodeUniqueValidator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void initialize(CurrencyCodeUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
        if (currencyCode == null) {
            return false;
        }
        if (currencyService.getCurrencyByCode(currencyCode) != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Constants.CURRENCY_TAKEN)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}