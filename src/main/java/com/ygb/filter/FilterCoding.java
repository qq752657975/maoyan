package com.ygb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class FilterCoding implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletRequest.setCharacterEncoding("UTF-8");

        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.setContentType("UTF-8");

        String originHeads = httpServletRequest.getHeader("Origin");

        httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeads);

        // 设置服务器允许浏览器发送请求都携带cookie
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        // 允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        //允许访问方式
        httpServletResponse.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With,Content-Type,Accept,mid,X-Token");



//        httpServletResponse.setContentType("text/jsonp;charset= UTF-8");


        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
