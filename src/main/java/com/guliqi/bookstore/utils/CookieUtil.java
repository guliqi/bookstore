package com.guliqi.bookstore.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {
    private static Cookie tokenCookie = new Cookie("token", "");

    public static Cookie getTokenCookie(String domain, String path, String value){
        tokenCookie.setDomain(domain);
        tokenCookie.setPath(path);
        tokenCookie.setValue(value);
        return tokenCookie;
    }
}
