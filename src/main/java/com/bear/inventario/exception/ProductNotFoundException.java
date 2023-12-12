package com.bear.inventario.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long productId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró el producto especificado", productId);
    }
}
