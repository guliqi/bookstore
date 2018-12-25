package com.guliqi.bookstore.utils;

import java.util.UUID;

public class CommonUtil {
    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
