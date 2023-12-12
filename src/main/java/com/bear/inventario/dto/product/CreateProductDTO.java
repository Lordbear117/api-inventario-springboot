package com.bear.inventario.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductDTO {

    @Schema(description = "Nombre del producto", example = "Televisor LED")
    @NotBlank
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Televisor de 55 pulgadas con resolución 4K")
    @NotBlank
    private String descripcion;

    @Schema(description = "Precio del producto", example = "1299.99")
    @NotNull
    private double precio;

    @Schema(description = "Cantidad disponible del producto", example = "50")
    @NotNull
    private int cantidad;
}
