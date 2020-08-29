package com.ygb.Controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class text {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @RequestMapping("/a")
    public void text(){

    }
}
