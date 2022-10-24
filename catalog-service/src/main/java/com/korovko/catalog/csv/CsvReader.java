package com.korovko.catalog.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Slf4j
public class CsvReader<T> {

  private static final char DELIMITER = ',';

  private final Class<T> targetClass;

  public CsvReader(final Class<T> targetClass) {
    this.targetClass = targetClass;
  }

  public List<T> read(final InputStream file) {
    log.debug("Reading .csv file");

    try (file; Reader reader = new InputStreamReader(file)) {
      return new CsvToBeanBuilder<T>(reader)
//          .withSeparator(DELIMITER)
          .withIgnoreLeadingWhiteSpace(true)
          .withThrowExceptions(false)
          .withType(targetClass)
          .build()
          .stream()
          .toList();
    } catch (IOException cause) {
      log.error("Exception is happened during reading .csv file");
      throw new RuntimeException("Exception is happened during reading .csv file");
    }
  }

}
