package com.korovko.inventory.exception;

public class ProductUnavailableException extends RuntimeException {

  public ProductUnavailableException(final String message) {
    super(message);
  }

}
