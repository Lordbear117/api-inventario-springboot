package com.bear.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SupplierProductKey {

    @Column(name = "proveedor_id")
    private long supplierId;

    @Column(name = "producto_id")
    private long productId;
}
