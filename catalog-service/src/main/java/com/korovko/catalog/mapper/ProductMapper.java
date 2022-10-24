package com.korovko.catalog.mapper;

import com.korovko.catalog.csv.ProductCsvRow;
import com.korovko.catalog.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductEntity toEntity(ProductCsvRow productCsvRow);

}
