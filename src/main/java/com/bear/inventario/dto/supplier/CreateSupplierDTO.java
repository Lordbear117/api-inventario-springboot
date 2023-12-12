package com.bear.inventario.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSupplierDTO {

    @Schema(description = "Nombre del proveedor", example = "ProveedorXYZ")
    @NotBlank
    private String nombre;

    @Schema(description = "Información de contacto del proveedor", example = "contacto@proveedor.com")
    @NotBlank
    private String contacto;

    @Schema(description = "Dirección del proveedor", example = "Calle Principal #123")
    @NotBlank
    private String direccion;
}
