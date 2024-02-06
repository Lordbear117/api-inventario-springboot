package com.bear.inventario.controller;

import com.bear.inventario.dto.product.CreateProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.product.UpdateProductDTO;
import com.bear.inventario.exception.ProductNotFoundException;
import com.bear.inventario.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoints de Productos",
     description = "CRUD de Invenario de Productos Electronicos")
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

  @Autowired private ProductService service;

  @Operation(summary = "Obtiene la lista de todos los productos")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDTO> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Crea un nuevo producto")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDTO save(@Valid @RequestBody CreateProductDTO data) {
    return service.save(data);
  }

  @Operation(summary = "Actualiza la información de un producto")
  @PutMapping("{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable long productId,
                     @Valid @RequestBody UpdateProductDTO data)
      throws ProductNotFoundException {
    service.update(productId, data);
  }

  @Operation(summary = "Borra la información de un producto")
  @DeleteMapping("{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long productId)
      throws ProductNotFoundException {
    service.delete(productId);
  }
}
