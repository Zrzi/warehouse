package com.database.warehouse.exception;

public class DeleteUnable extends RuntimeException {

    private final static String message = "不能删除";

    public DeleteUnable() {
        super(message);
    }

}
