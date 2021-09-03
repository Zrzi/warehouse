package com.database.warehouse.exception;

public class DepartmentNotFound extends RuntimeException {

    private final static String message = "部门不存在";

    public DepartmentNotFound() {
        super(message);
    }

}
