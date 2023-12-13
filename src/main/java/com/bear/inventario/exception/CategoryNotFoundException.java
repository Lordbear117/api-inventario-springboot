package com.bear.inventario.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(long categoryId) {
        super("ERR_DATA_NOT_FOUND", "No se encontró la categoria especificada", categoryId);
    }

}
