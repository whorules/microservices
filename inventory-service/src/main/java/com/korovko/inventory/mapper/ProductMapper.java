package com.korovko.inventory.mapper;

import com.korovko.inventory.csv.ProductCsvRow;
import com.korovko.inventory.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "isAvailable", ignore = true)
  ProductEntity toEntity(ProductCsvRow productCsvRow);

}
