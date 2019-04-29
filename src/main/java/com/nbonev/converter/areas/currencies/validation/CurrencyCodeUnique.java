package com.nbonev.converter.areas.currencies.validation;

import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyCodeUniqueValidator.class)
public @interface CurrencyCodeUnique {
    String message() default "Currency already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
