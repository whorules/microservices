package com.korovko.catalog.config;

import com.korovko.catalog.csv.CsvReader;
import com.korovko.catalog.csv.ProductCsvRow;
import com.korovko.catalog.mapper.ProductMapper;
import com.korovko.catalog.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

  private final CatalogRepository repository;
  private final ProductMapper     productMapper;
  private final CsvReader<ProductCsvRow> csvReader = new CsvReader<>(ProductCsvRow.class);

  @Value("classpath:/static/jcpenney_com-ecommerce_sample.csv")
  private Resource resource;

  @SneakyThrows
  @PostConstruct
  public void init() {
    List<ProductCsvRow> csvData = csvReader.read(resource.getInputStream());
    repository.saveAll(csvData.stream().map(productMapper::toEntity).collect(Collectors.toList()));
  }

}
