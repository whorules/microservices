package com.korovko.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable {

  @Id
  @Column(name = "uniq_id")
  private String  id;
  private String  sku;
  private String  nameTitle;
  @Lob
  private String  description;
  private Double  listPrice;
  private Double  salePrice;
  private String  category;
  private String  categoryTree;
  private String  averageProductRating;
  @Lob
  private String  productUrl;
  @Lob
  private String  productImageUrls;
  private String  brand;
  private Integer totalReviewsNumber;
  @Lob
  private String  reviews;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final ProductEntity that = (ProductEntity) o;
    return sku != null && Objects.equals(sku, that.sku);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
