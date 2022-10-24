package com.korovko.registry.service;

import com.korovko.registry.config.ConfigProperties;
import com.korovko.registry.dto.Product;
import com.korovko.registry.dto.ProductAvailability;
import com.korovko.registry.exception.CircuitBreakerException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final RestTemplate     restTemplate;
  private final ConfigProperties configProperties;

  @HystrixCommand(
      commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")},
      fallbackMethod = "handleGetProduct")
  public Product getById(final String productId) {
    Optional<Product> optionalProduct = getByProductId(productId);
    Optional<ProductAvailability> productAvailability = optionalProduct
        .map(product -> makeInventoryServiceCall(Collections.singletonList(productId)))
        .stream()
        .flatMap(Collection::stream)
        .findFirst()
        .filter(ProductAvailability::getIsAvailable);
    return productAvailability.isPresent() ? optionalProduct.get() : null;
  }

  @HystrixCommand(
      commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")},
      fallbackMethod = "handleGetProductsBySku")
  public List<Product> getBySku(final String sku) {
    Map<String, Product> productsById = getProductListBySku(sku)
        .stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
    List<ProductAvailability> productAvailabilities = makeInventoryServiceCall(new ArrayList<>(productsById.keySet()));
    return productAvailabilities
        .stream()
        .filter(ProductAvailability::getIsAvailable)
        .map(productAvailability -> productsById.get(productAvailability.getId()))
        .collect(Collectors.toList());

  }

  @SuppressWarnings("unused")
  public Product handleGetProduct(final String productId) {
    throw new CircuitBreakerException("Service temporarily unavailable");
  }

  @SuppressWarnings("unused")
  public List<Product> handleGetProductsBySku(final String sku) {
    throw new CircuitBreakerException("Service temporarily unavailable");
  }

  private Optional<Product> getByProductId(final String productId) {
    return Optional.ofNullable(restTemplate
        .getForEntity(configProperties.catalogServiceUrl() +
            configProperties.catalogService().productByIdApiName(), Product.class, productId)
        .getBody());
  }

  private List<Product> getProductListBySku(final String sku) {
    return Optional
        .ofNullable(restTemplate
            .getForEntity(
                configProperties.catalogServiceUrl() + configProperties.catalogService().listBySkuApiName() + "?sku=" +
                    sku, Product[].class)
            .getBody())
        .map(Arrays::asList)
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private List<ProductAvailability> makeInventoryServiceCall(@NonNull final List<String> productIds) {
    return Optional
        .ofNullable(restTemplate
            .getForEntity(
                configProperties.inventoryServiceUrl() + configProperties.inventoryService().productsByIdsApiName() +
                    buildRequestParamsPath(productIds), ProductAvailability[].class)
            .getBody())
        .map(Arrays::asList)
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private String buildRequestParamsPath(@NonNull final List<String> productIds) {
    if (productIds.isEmpty()) {
      throw new RuntimeException("At least 1 product id must be provided for a service call");
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("?productId=").append(productIds.get(0));
    for (int i = 1; i < productIds.size(); i++) {
      stringBuilder.append("&productId=").append(productIds.get(i));
    }
    return stringBuilder.toString();
  }

}
