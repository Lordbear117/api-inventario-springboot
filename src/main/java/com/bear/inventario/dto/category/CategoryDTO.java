package com.bear.inventario.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDTO {

    @Schema(description = "Identificador de la categoria", example = "1")
    private int id;

    @Schema(description = "Nombre de la categoria", example = "Computadoras")
    private String nombre;
}
