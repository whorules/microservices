package com.korovko.registry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "clients")
public record ConfigProperties(CatalogService catalogService, InventoryService inventoryService) {

  public String catalogServiceUrl() {
    return catalogService.url;
  }

  public String inventoryServiceUrl() {
    return inventoryService.url;
  }

  public record CatalogService(String url, Map<String, String> apis) {

    public String productByIdApiName() {
      return apis.get("product-by-id");
    }

    public String listBySkuApiName() {
      return apis.get("list-by-sku");
    }

  }

  public record InventoryService(String url, Map<String, String> apis) {

    public String productsByIdsApiName() {
      return apis.get("products-by-ids");
    }

  }

}
