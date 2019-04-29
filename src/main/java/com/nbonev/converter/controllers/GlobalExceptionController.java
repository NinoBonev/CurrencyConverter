package com.nbonev.converter.controllers;

import com.nbonev.converter.errors.ResourceNotFoundException;
import com.nbonev.converter.errors.UnauthorizedException;
import com.nbonev.converter.util.ApplicationConstants;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionController extends BaseController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView getException(RuntimeException e) {
        String errorMessage =
                e.getClass().isAnnotationPresent(ResponseStatus.class)
                        ? e.getClass().getAnnotation(ResponseStatus.class).reason()
                        : ApplicationConstants.DEFAULT_ERROR_MESSAGE;

        return this.view("error/error-template", "error", errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView unauthorized() {
        return this.getException(new UnauthorizedException());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView notFound() {
        return this.view("error/404");
    }

}
