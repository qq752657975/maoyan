package com.ygb.entity;

import org.springframework.stereotype.Component;

//系统日志
@Component
public class SysLogVo implements java.io.Serializable {


	private long sysLogId;  //编号
    private long managerId; //管理员id
    private String userName; //管理员名
    private int logType; //日志类型
    private String content; //日志内容
    private String createDate; //创建时间
    private String ipAddress; //ip地址
    private String sdate; //开始时间
    private String edate; //结束时间
    private String[] logTypes={"新增","修改","删除","登陆","修改密码","延期"};

    public String getLogTypes(){
        return logTypes[logType-1];
    }
    public long getSysLogId() {
        return sysLogId;
    }

    public void setSysLogId(long sysLogId) {
        this.sysLogId = sysLogId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

}