package com.database.warehouse.exception;

public class ModifyUnable extends RuntimeException {

    private final static String message = "不能修改";

    public ModifyUnable() {
        super(message);
    }

}
