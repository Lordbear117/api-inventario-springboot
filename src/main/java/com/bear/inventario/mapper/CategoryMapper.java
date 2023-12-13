package com.bear.inventario.mapper;

import com.bear.inventario.dto.category.CategoryDTO;
import com.bear.inventario.dto.category.CreateCategoryDTO;
import com.bear.inventario.dto.category.UpdateCategoryDTO;
import com.bear.inventario.model.Category;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
  CategoryDTO toDTO(Category model);

  List<CategoryDTO> toDTO(List<Category> model);

  @Mapping(target = "id", ignore = true)
  Category toModel(CreateCategoryDTO dto);

  @Mapping(target = "id", ignore = true)
  void update(@MappingTarget Category category, UpdateCategoryDTO data);
}
