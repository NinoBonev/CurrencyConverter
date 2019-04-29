package com.nbonev.converter.errors;

import com.nbonev.converter.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ApplicationConstants.NOT_FOUND_ERROR_MESSAGE)
public class ResourceNotFoundException extends RuntimeException {
}
