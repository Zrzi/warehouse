package com.database.warehouse.exception;

public class ProductExist extends RuntimeException {

    private final static String message = "铲平存在";

    public ProductExist() {
        super(message);
    }

}
