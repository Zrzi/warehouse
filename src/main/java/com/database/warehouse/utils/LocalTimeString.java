package com.database.warehouse.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeString {

    private final static String format = "yyyy-MM-dd HH:mm:ss";
    private final static String costFormat = "yyyy-MM";

    public static String getLocalTimeNow() {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static LocalDateTime toLocalDateTime(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    public static String getCostString() {
        return DateTimeFormatter.ofPattern(costFormat).format(LocalDateTime.now());
    }

    public static String getNextMonth() {
        return DateTimeFormatter.ofPattern(costFormat).format(LocalDateTime.now().minusMonths(1));
    }

}
