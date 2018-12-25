package com.guliqi.bookstore.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String MD5Encode(String origin){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(origin.getBytes());
            return DatatypeConverter.printHexBinary(bytes);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

}
