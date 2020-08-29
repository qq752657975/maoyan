package com.ygb.util;

import org.springframework.stereotype.Component;

@Component
public class BaseConstants {

	public static final int LOG_ADD = 1; //新增
	public static final int LOG_UPDATE = 2; //修改
	public static final int LOG_DEL = 3; //删除
	public static final int LOG_LOGIN = 4; //登录
	public static final int LOG_PWD = 5; //修改密码
	public static final String SAVE_PATH="/card/";
	public static String absPath = "D:/img";
	public static String code;
	
}

