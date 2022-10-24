package com.korovko.inventory.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtils {

  /**
   * Generates random boolean value for a product availability.
   * 80% that the product is available and 20% that is not
   * @return if the product is available
   */
  public boolean isAvailable() {
    int percentage = new Random().nextInt(99) + 1;
    return percentage <= 80;
  }

}
