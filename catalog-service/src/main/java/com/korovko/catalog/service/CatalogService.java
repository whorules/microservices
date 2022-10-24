package com.korovko.catalog.service;

import com.korovko.catalog.entity.ProductEntity;
import com.korovko.catalog.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogService {

  private final CatalogRepository repository;

  public ProductEntity getProductById(final String productId) {
    return repository.findById(productId).orElseThrow(() -> {
      log.error("Product with id {} not found", productId);
      throw new RuntimeException("Product with id " + productId + " not found");
    });
  }

  public List<ProductEntity> getProductsBySku(final String sku) {
    List<ProductEntity> products = repository.findAllBySku(sku);
    log.info("Found {} products by sku {}", products.size(), sku);
    return products;
  }

}
