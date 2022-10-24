package com.korovko.inventory.service;

import com.korovko.inventory.entity.ProductEntity;
import com.korovko.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository repository;

  public List<ProductEntity> getProductById(final List<String> productIds) {
    return repository.findAllById(productIds);
  }

}
