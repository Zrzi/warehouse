package com.database.warehouse.exception;

public class UpdateInfoException extends RuntimeException {

    private final static String message = "系统内部错误";

    public UpdateInfoException() {
        super(message);
    }

}
