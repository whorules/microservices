package com.korovko.registry.controller;

import com.korovko.registry.dto.Product;
import com.korovko.registry.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/{productId}")
  public Product getById(@PathVariable final String productId) {
    return productService.getById(productId);
  }

  @GetMapping
  public List<Product> getBySku(@RequestParam final String sku) {
    return productService.getBySku(sku);
  }

}
