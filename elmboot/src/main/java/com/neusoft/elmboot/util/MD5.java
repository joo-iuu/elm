package com.neusoft.elmboot.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * 生成MD5
     * @param str
     * @return
     */
    public static String encode(String str) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String substring = new BigInteger(1, md.digest()).toString(16).substring(8, 24);
            return substring;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

}

