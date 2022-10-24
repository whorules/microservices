package com.korovko.inventory.config;

import com.korovko.inventory.csv.CsvReader;
import com.korovko.inventory.csv.ProductCsvRow;
import com.korovko.inventory.entity.ProductEntity;
import com.korovko.inventory.mapper.ProductMapper;
import com.korovko.inventory.repository.InventoryRepository;
import com.korovko.inventory.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

  private final InventoryRepository repository;
  private final ProductMapper       productMapper;
  private final CsvReader<ProductCsvRow> csvReader = new CsvReader<>(ProductCsvRow.class);

  @Value("classpath:/static/jcpenney_com-ecommerce_sample.csv")
  private Resource resource;

  @SneakyThrows
  @PostConstruct
  public void init() {
    List<ProductCsvRow> csvData = csvReader.read(resource.getInputStream());

    List<ProductEntity> entities = csvData
        .stream()
        .map(productMapper::toEntity)
        .peek(productEntity -> productEntity.setIsAvailable(RandomUtils.isAvailable()))
        .toList();
    repository.saveAll(entities);
  }

}
