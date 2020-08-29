package com.ygb.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyConfig implements WebMvcConfigurer {


    //以这种方式将拦截器注入为一个bean，可以防止拦截器中无法注入bean的问题出现
    @Bean
    public AdminInterceptor apiInterceptor(){
        return new AdminInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        InterceptorRegistration registration = registry.addInterceptor(apiInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/**/login.jsp",            //登录
                "/**/login",//验证码
                "/**/*.html",            //html静态资源
                "/**/*.xls",             //Excel文件静态资源
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif"



        );
    }
    //设置首页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }


}
