package com.database.warehouse.exception;

public class MaxException extends RuntimeException {

    private final static String message = "超过上限";

    public MaxException() {
        super(message);
    }

}
