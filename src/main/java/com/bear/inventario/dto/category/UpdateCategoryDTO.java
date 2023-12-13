package com.bear.inventario.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCategoryDTO {

    @Schema(description = "Nombre de la categoria", example = "Computadora")
    @NotBlank
    private String nombre;
}
