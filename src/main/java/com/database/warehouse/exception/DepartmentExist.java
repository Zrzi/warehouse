package com.database.warehouse.exception;

public class DepartmentExist extends RuntimeException {

    private final static String message = "部门已存在";

    public DepartmentExist() {
        super(message);
    }
}
