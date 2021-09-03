package com.database.warehouse.entity;

import java.util.HashMap;

public class ResponseData {

    private Integer code;
    private final HashMap<String, Object> data = new HashMap<>();
    private String message;

    private ResponseData() {}

    private ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public static ResponseData success() {
        return new ResponseData(200, "ok");
    }

    public static ResponseData fail(String message) {
        return new ResponseData(500, message);
    }

}
