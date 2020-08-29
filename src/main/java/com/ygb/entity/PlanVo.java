package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//排片类
@Component
public class PlanVo implements Serializable {

	private int planId; //编号
	private int hallId; //放映厅id
	private int movieId; //电影id
	private int agentId; //影院id
	private int playTimeId; //排片场次
	private  float seatPrice;  //单价
	private String playDay; //排片日期
	private String playTimeName; //排名名
	private String startTime; //开始时间
	private String endTime; //结束时间
	private String hallName;  //放映厅名
	private String movieName;  //电影名
	private String agentName; //影院名
	private String hallTypeName; //放映厅类型
	private String remark;//备注

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHallTypeName() {
		return hallTypeName;
	}

	public void setHallTypeName(String hallTypeName) {
		this.hallTypeName = hallTypeName;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getPlayTimeId() {
		return playTimeId;
	}

	public void setPlayTimeId(int playTimeId) {
		this.playTimeId = playTimeId;
	}

	public float getSeatPrice() {
		return seatPrice;
	}

	public void setSeatPrice(float seatPrice) {
		this.seatPrice = seatPrice;
	}

	public String getPlayDay() {
		return playDay;
	}

	public void setPlayDay(String playDay) {
		this.playDay = playDay;
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		return "编号="+planId+",电影id="+movieId+",播放时段id="+playTimeId+",票价="+seatPrice;
	}
}
