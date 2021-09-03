package com.database.warehouse.exception;

public class MinException extends RuntimeException {

    private final static String message = "低于下限";

    public MinException() {
        super(message);
    }
}
