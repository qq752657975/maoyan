package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//影厅类
@Component
public class MovieHallVo implements Serializable {
    private Integer hallId; //主键
    private String hallName;//影厅名称
    private int agentId;//所属影院
    private int seatNum; //总的座位数
    private int row; //排数
    private int col;//每排座位数
    private int hallTypeId;//影厅类别
    private int state;//0不可用，1可用
    private String remark;//备注
    private String hallTypeName;  //放映厅名
    private String agentName; //影院名

    public String getHallTypeName() {
        return hallTypeName;
    }

    public void setHallTypeName(String hallTypeName) {
        this.hallTypeName = hallTypeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "编号："+hallId+",影厅名称："+hallName;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getHallTypeId() {
        return hallTypeId;
    }

    public void setHallTypeId(int hallTypeId) {
        this.hallTypeId = hallTypeId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
