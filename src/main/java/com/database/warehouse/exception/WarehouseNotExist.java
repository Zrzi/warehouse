package com.database.warehouse.exception;

public class WarehouseNotExist extends RuntimeException {

    private final static String message = "仓库不存在";

    public WarehouseNotExist() {
        super(message);
    }

}
