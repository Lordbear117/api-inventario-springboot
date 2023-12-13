package com.bear.inventario.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductDTO {

    @Schema(description = "Identificador del producto", example = "1")
    private int id;

    @Schema(description = "Nombre del producto", example = "Televisor LED")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Televisor de 55 pulgadas con resolución 4K")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "1299.99")
    private double precio;

    @Schema(description = "Cantidad disponible del producto", example = "50")
    private int cantidad;

}
