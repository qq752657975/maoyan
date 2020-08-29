package com.ygb.util;

public class text {
    public static void main(String[] args) {
        String s = AesUtils.encryptStr("1", AesUtils.SECRETKEY);
        System.out.println(s);
    }
}
