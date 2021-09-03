package com.database.warehouse.exception;

public class UsernamePasswordNotMatch extends RuntimeException {

    private final static String message = "用户名或者密码错误";

    public UsernamePasswordNotMatch() {
        super(message);
    }

}
