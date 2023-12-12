package com.bear.inventario.mapper;

import java.util.List;

import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.product.CreateProductDTO;
import com.bear.inventario.dto.product.UpdateProductDTO;
import com.bear.inventario.model.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    ProductDTO toDTO(Product model);

    List<ProductDTO> toDTO(List<Product> model);

    @Mapping(target = "id", ignore = true)
    Product toModel(CreateProductDTO dto);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Product product, UpdateProductDTO data);
}
