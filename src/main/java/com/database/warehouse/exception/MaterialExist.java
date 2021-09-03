package com.database.warehouse.exception;

public class MaterialExist extends RuntimeException {

    private final static String message = "材料存在";

    public MaterialExist() {
        super(message);
    }

}
