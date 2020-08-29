package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//放映时间
@Component
public class PlayTimeVo implements Serializable {
    private Integer playTimeId; //编号
    private String playTimeName; //播放时段
    private String startTime; //开始时间
    private String endTime; //结束时间

    @Override
    public String toString() {
        return "编号："+playTimeId+",播放时段："+playTimeName+",开始时间："+startTime+",结束时间:"+endTime;
    }

    public Integer getPlayTimeId() {
        return playTimeId;
    }

    public void setPlayTimeId(Integer playTimeId) {
        this.playTimeId = playTimeId;
    }

    public String getPlayTimeName() {
        return playTimeName;
    }

    public void setPlayTimeName(String playTimeName) {
        this.playTimeName = playTimeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
