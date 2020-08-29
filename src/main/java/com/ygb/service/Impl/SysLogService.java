package com.ygb.service.Impl;


import com.ygb.Dao.SysLogDAO;
import com.ygb.entity.Manager;
import com.ygb.entity.SysLogVo;
import com.ygb.service.IAreaService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SysLogService implements ISysLogService {
    @Resource
    SysLogDAO sysLogDAO;

    @Transactional
    public List<SysLogVo> list(SysLogVo log, PageObject pager){
        pager.setPageRow(9);
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return sysLogDAO.list(log,pager);
    }

    @Transactional
    public int count(SysLogVo log){
        return sysLogDAO.count(log);
    }



    @Transactional
    public void add(int logType,String content){
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

        syslog.setIpAddress(request.getRemoteAddr());
        sysLogDAO.add(syslog);
    }
}