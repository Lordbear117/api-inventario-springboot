package com.bear.inventario.controller;

import com.bear.inventario.dto.category.CategoryDTO;
import com.bear.inventario.dto.category.CreateCategoryDTO;
import com.bear.inventario.dto.category.UpdateCategoryDTO;
import com.bear.inventario.exception.CategoryNotFoundException;
import com.bear.inventario.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoints de Categorias",
     description = "CRUD de Invenario de Productos Electronicos")
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

  @Autowired private CategoryService service;

  @Operation(summary = "Obtiene la lista de todas las categorias")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDTO> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Crea una nueva categoria")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDTO save(@Valid @RequestBody CreateCategoryDTO data) {
    return service.save(data);
  }

  @Operation(summary = "Actualiza la información de una categoria")
  @PutMapping("{categoryId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable long categoryId,
                     @Valid @RequestBody UpdateCategoryDTO data)
      throws CategoryNotFoundException {
    service.update(categoryId, data);
  }

  @Operation(summary = "Borra la información de una categoria")
  @DeleteMapping("{categoryId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long categoryId)
      throws CategoryNotFoundException {
    service.delete(categoryId);
  }
}
