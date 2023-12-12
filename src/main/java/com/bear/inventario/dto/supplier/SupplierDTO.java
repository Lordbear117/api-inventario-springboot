package com.bear.inventario.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SupplierDTO {

    @Schema(description = "Identificador del proveedor", example = "1")
    private int id;

    @Schema(description = "Nombre del proveedor", example = "ProveedorXYZ")
    private String nombre;

    @Schema(description = "Información de contacto del proveedor", example = "contacto@proveedor.com")
    private String contacto;

    @Schema(description = "Dirección del proveedor", example = "Calle Principal #123")
    private String direccion;
}
