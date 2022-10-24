package com.korovko.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class CircuitBreakerException extends RuntimeException {

  public CircuitBreakerException(final String message) {
    super(message);
  }

}
