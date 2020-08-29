package com.ygb.config;





import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.transaction.annotation.EnableTransactionManagement;




@Configuration
@Import({RedisConfig.class,RedisCacheConfig.class,MysqlConfig.class,MysqlConfig.class}) //引入另外的配置类
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true) //开启事务
@EnableCaching //开启缓存
@ComponentScan(basePackages = {"com.ygb.util","com.ygb.entity","com.ygb.dbBean", "com.ygb.service","com.ygb.Dao","com.ygb.interceptor"})
public class SpringConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }




}
