package com.korovko.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String  id;
  private String  sku;
  private String  nameTitle;
  private String  description;
  private Double  listPrice;
  private Double  salePrice;
  private String  category;
  private String  categoryTree;
  private String  averageProductRating;
  private String  productUrl;
  private String  productImageUrls;
  private String  brand;
  private Integer totalReviewsNumber;
  private String  reviews;

}
