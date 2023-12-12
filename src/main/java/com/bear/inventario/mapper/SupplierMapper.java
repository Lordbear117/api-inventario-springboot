package com.bear.inventario.mapper;

import java.util.List;

import com.bear.inventario.dto.supplier.SupplierDTO;
import com.bear.inventario.dto.supplier.CreateSupplierDTO;
import com.bear.inventario.dto.supplier.UpdateSupplierDTO;
import com.bear.inventario.model.Supplier;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface SupplierMapper {

    SupplierDTO toDTO(Supplier model);

    List<SupplierDTO> toDTO(List<Supplier> model);

    @Mapping(target = "id", ignore = true)
    Supplier toModel(CreateSupplierDTO dto);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Supplier supplier, UpdateSupplierDTO data);
}
