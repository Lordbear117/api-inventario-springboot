package com.bear.inventario.service;

import com.bear.inventario.dto.category.CategoryDTO;
import com.bear.inventario.dto.category.CreateCategoryDTO;
import com.bear.inventario.dto.category.UpdateCategoryDTO;
import com.bear.inventario.exception.CategoryNotFoundException;
import com.bear.inventario.mapper.CategoryMapper;
import com.bear.inventario.model.Category;
import com.bear.inventario.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired private CategoryRepository repository;

  @Autowired private CategoryMapper mapper;

  public List<CategoryDTO> findAll() {
    return mapper.toDTO(repository.findAll());
  }

  public CategoryDTO save(CreateCategoryDTO data) {
    Category entity = repository.save(mapper.toModel(data));
    return mapper.toDTO(entity);
  }

  public void update(long categorytId, UpdateCategoryDTO data)
      throws CategoryNotFoundException {
    Optional<Category> result = repository.findById(categorytId);

    if (!result.isPresent()) {
      throw new CategoryNotFoundException(categorytId);
    }

    Category category = result.get();

    // Aplicar los cambios al artista
    mapper.update(category, data);

    repository.save(category);
  }

  public void delete(long categorytId)throws CategoryNotFoundException {
    Optional<Category> result = repository.findById(categorytId);

    if (!result.isPresent()) {
      throw new CategoryNotFoundException(categorytId);
    } else {
      repository.deleteById(categorytId);
    }
  }
}
