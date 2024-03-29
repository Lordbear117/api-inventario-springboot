package com.bear.inventario.controller;

import com.bear.inventario.dto.product.AddProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.supplier.CreateSupplierDTO;
import com.bear.inventario.dto.supplier.SupplierDTO;
import com.bear.inventario.dto.supplier.UpdateSupplierDTO;
import com.bear.inventario.exception.SupplierNotFoundException;
import com.bear.inventario.service.SupplierProductService;
import com.bear.inventario.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoints de Proveedores",
     description = "CRUD de Invenario de Productos Electronicos")
@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {

  private final SupplierService supplierService;
  private final SupplierProductService supplierProductService;

  @Autowired
  public SupplierController(SupplierService supplierService, SupplierProductService supplierProductService) {
    this.supplierService = supplierService;
    this.supplierProductService = supplierProductService;
  }

  @Operation(summary = "Obtiene la lista de todos los proveedores")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<SupplierDTO> findAll() {
    return supplierService.findAll();
  }

  @Operation(summary = "Crea un nuevo proveedor")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SupplierDTO save(@Valid @RequestBody CreateSupplierDTO data) {
    return supplierService.save(data);
  }

  @Operation(summary = "Actualiza la información de un proveedor")
  @PutMapping("{supplierId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable long supplierId,
                     @Valid @RequestBody UpdateSupplierDTO data)
      throws SupplierNotFoundException {
    supplierService.update(supplierId, data);
  }

  @Operation(summary = "Borra la información de un proveedor")
  @DeleteMapping("{supplierId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long supplierId)
      throws SupplierNotFoundException {
    supplierService.delete(supplierId);
  }

  @Operation(summary = "Asocia un producto a un proveedor")
  @PostMapping("{supplierId}/products")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addProduct(@PathVariable long supplierId,
                         @RequestBody AddProductDTO data) {
    supplierProductService.addProduct(supplierId, data.getProductId());
  }

  @Operation(summary = "Obtiene los productos de un proveedor determinado")
  @GetMapping("{supplierId}/products")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDTO>
  findProductsBySupplier(@PathVariable long supplierId) {
    return supplierProductService.findProductsBySupplier(supplierId);
  }
}