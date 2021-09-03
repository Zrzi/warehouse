package com.database.warehouse.exception;

public class MaterialNotFound extends RuntimeException {

    private final static String message = "材料不存在";

    public MaterialNotFound() {
        super(message);
    }

}
