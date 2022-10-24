package com.korovko.inventory.controller;

import com.korovko.inventory.entity.ProductEntity;
import com.korovko.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory/products")
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping
  public List<ProductEntity> getById(@RequestParam(name = "productId") final List<String> productIds) {
    return inventoryService.getProductById(productIds);
  }

}
