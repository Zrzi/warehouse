package com.database.warehouse.exception;

public class EmployeeExist extends RuntimeException {

    private final static String message = "用户名已存在";

    public EmployeeExist() {
        super(message);
    }

}
