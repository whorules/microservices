package com.korovko.catalog.controller;

import com.korovko.catalog.entity.ProductEntity;
import com.korovko.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class CatalogController {

  private final CatalogService catalogService;

  @GetMapping("/{productId}")
  public ProductEntity getById(@PathVariable final String productId) {
    return catalogService.getProductById(productId);
  }

  @GetMapping
  public List<ProductEntity> getListBySku(@RequestParam final String sku) {
    return catalogService.getProductsBySku(sku);
  }

}
