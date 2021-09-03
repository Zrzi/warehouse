package com.database.warehouse.exception;

public class EmployeeNotFound extends RuntimeException{

    private final static String message = "用户不存在";

    public EmployeeNotFound() {
        super(message);
    }

}
