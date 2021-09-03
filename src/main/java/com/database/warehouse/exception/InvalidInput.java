package com.database.warehouse.exception;

public class InvalidInput extends RuntimeException {

    private final static String message = "无效输入";

    public InvalidInput() {
        super(message);
    }

}
