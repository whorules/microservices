package com.korovko.catalog.repository;

import com.korovko.catalog.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<ProductEntity, String> {

  List<ProductEntity> findAllBySku(final String sku);
}
