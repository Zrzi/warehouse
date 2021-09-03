package com.database.warehouse.exception;

public class WarehouseExist extends RuntimeException {

    private final static String message = "仓库已经存在";

    public WarehouseExist() {
        super(message);
    }

}
