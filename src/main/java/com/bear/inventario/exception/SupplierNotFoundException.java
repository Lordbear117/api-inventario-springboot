package com.bear.inventario.exception;

public class SupplierNotFoundException extends RuntimeException {

    public SupplierNotFoundException(long supplierId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el proveedor especificado", supplierId);
    }
}
