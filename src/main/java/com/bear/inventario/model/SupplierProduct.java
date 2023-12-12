package com.bear.inventario.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "proveedor_producto")
public class SupplierProduct {

    @EmbeddedId
    private SupplierProductKey id;

    @ManyToOne
    @MapsId("supplierId")
    @JoinColumn(name = "proveedor_id")
    private Supplier supplier;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "producto_id")
    private Product product;
}
