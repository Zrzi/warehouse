package com.database.warehouse.exception;

public class UpdateUnable extends RuntimeException {

    private static String message = "不能修改";

    public UpdateUnable() {
        super(message);
    }
}
