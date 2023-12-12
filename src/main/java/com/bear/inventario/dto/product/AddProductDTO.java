package com.bear.inventario.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddProductDTO {
    @Schema(description = "Identificador del producto a asociar", example = "10")
    private long productId;
}
