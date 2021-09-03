package com.database.warehouse.exception;

public class ProductNotFound extends RuntimeException {

    private final static String message = "产品不存在";

    public ProductNotFound() {
        super(message);
    }
}
