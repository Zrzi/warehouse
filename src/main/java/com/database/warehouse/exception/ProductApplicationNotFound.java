package com.database.warehouse.exception;

public class ProductApplicationNotFound extends RuntimeException {

    private final static String message = "入库请求不存在";

    public ProductApplicationNotFound() {
        super(message);
    }
}
