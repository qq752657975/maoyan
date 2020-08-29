package com.ygb.util;


import com.ygb.entity.Manager;
import com.ygb.entity.SysLogVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ContextUtils {
	public static ConditionBean getCondition(String sessionname){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		ConditionBean condition =  (ConditionBean)request.getSession().getAttribute(sessionname);
		return condition;
	}
	public static Manager getUserInfo(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();

		Manager userinfo =  (Manager)request.getSession().getAttribute("userinfo");

		return userinfo;
	}
	
	public static String getCode(String str, String sep){
		String temp = "";
		int pos = str.indexOf(sep);
		if(pos>0)
			temp = str.substring(0,pos);
		return temp;
	}

	public static SysLogVo log(int logType, String content){
		/**
		 * logType
		 * 1.新增
		 * 2.修改
		 * 3.删除
		 * 4.登录
		 * 
		 * */
		switch(logType){
			case BaseConstants.LOG_ADD:
				content = "新增："+content;
				break;
			case BaseConstants.LOG_UPDATE:
				content = "修改："+content;
				break;
			case BaseConstants.LOG_DEL:
				content = "删除："+content;
				break;
			case BaseConstants.LOG_LOGIN:
				content = "登录："+content;
				break;
			case BaseConstants.LOG_PWD:
				content = "修改密码："+content;
				break;
		}
		if(content.length()>500){
			content=content.substring(0,500);
		}
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Manager userinfo =  (Manager)request.getSession().getAttribute("userinfo");
		long userId = userinfo == null ? 0 : userinfo.getManagerId();
		String userName = userinfo == null ? "" : userinfo.getManagerName();
		SysLogVo syslog = new SysLogVo();
		syslog.setManagerId(userId);
		syslog.setUserName(userName);
		syslog.setLogType(logType);
		syslog.setContent(content);
//		syslog.setCreateDate(new Date());
		syslog.setIpAddress(request.getRemoteAddr());

		return syslog;

	}
	public static String dateToStr(Date date){
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String d=dateformat1.format(date);
		return d;

	}
	public static String dateToShortStr(Date date){
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String d=dateformat1.format(date);
		return d;

	}


	public static String doubleToStr(double d){
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance(); 
		nf.setGroupingUsed(false); 
		return nf.format(d);
	}

	public static String produceExchangeCode(){
		Date d = new Date();
		int year = (d.getYear()+1900) % 100;
		int month = d.getMonth()+1;
		int day = d.getDate();
		String sdate = year+"";
		if(month<10)
			sdate += "0"+month;
		else
			sdate += month;
		if(day<10){
			sdate += "0"+day;
		}else{
			sdate += day;
		}
		return sdate;
	}
	public static String getFilename(){
		Date d = new Date();
		int year = d.getYear()+1900;
		int month = d.getMonth()+1;
		int day = d.getDate();
		int hour = d.getHours();
		int minute = d.getMinutes();
		int second = d.getSeconds();
		String sdate = year+"";
		if(month<10)
			sdate += "0"+month;
		else
			sdate += month;
		if(day<10){
			sdate += "0"+day;
		}else{
			sdate += day;
		}
		if(hour<10){
			sdate += "0"+hour;
		}else{
			sdate += hour;
		}
		if(minute<10){
			sdate += "0"+minute;
		}else{
			sdate += minute;
		}
		if(second<10){
			sdate += "0"+second;
		}else{
			sdate += second;
		}
		return sdate;
	}

	public static String getStr(String str){
		try {
			str = new String(str.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	public static String htmlspecialchars(String str) {
		if(str==null)
			return "";
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
}
