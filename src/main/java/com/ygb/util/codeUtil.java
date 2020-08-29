package com.ygb.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class codeUtil {



    public static String getCode(int length){
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for(int i = 0;i<length;i++){
            code.append(random.nextInt(10));
        }

        return code.toString();
    }
}
