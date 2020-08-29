package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

//购票信息
@Component
public class OrderVo implements Serializable {
    private  int orderId; //id
    private long orderNumber; //订单号
    private int planId; //排片id
    private String mobile; //  购票用户
    private int hallSeatId; // 座位id
    private float salePrice; //单价
    private String payType; //微信,支付宝
    private String orderTime; //购买时间
    private String state;//1购票，2退票
    private int movieId; //电影id
    private int inval; //1已支付 2未支付 -1已失效


    private String  agentName; //影院名
    private String  hallName; //放映厅id
    private String  movieName;  //电影名
    private String  playTimeName; //
    private String  startTime;
    private String  endTime;
    private String seatName;
    private String playDay; //

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getInval() {
        return inval;
    }

    public void setInval(int inval) {
        this.inval = inval;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPlayDay() {
        return playDay;
    }

    public void setPlayDay(String playDay) {
        this.playDay = playDay;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getHallSeatId() {
        return hallSeatId;
    }

    public void setHallSeatId(int hallSeatId) {
        this.hallSeatId = hallSeatId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "用户:"+mobile +",planId："+planId+",座位:"+hallSeatId+",价格："+salePrice+",支付方式:"+payType;
    }
}
