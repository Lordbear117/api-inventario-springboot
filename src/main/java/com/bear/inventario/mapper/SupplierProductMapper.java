package com.bear.inventario.mapper;

import com.bear.inventario.model.SupplierProduct;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SupplierProductMapper {
    @Mapping(source = "supplierId", target = "id.supplierId")
    @Mapping(source = "supplierId", target = "supplier.id")
    @Mapping(source = "productId", target = "id.productId")
    @Mapping(source = "productId", target = "product.id")
    SupplierProduct toModel(Long supplierId, Long productId);
}
