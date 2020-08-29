package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//座位
@Component
public class HallSeatVo implements Serializable {

	private Integer hallSeatId; //id
	private int hallId; //放映厅id
	private String seatName;  //座位名
	private int state; //状态：1可售，2停售

	public Integer getHallSeatId() {
		return hallSeatId;
	}

	public void setHallSeatId(Integer hallSeatId) {
		this.hallSeatId = hallSeatId;
	}

	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "编号="+hallSeatId+",影厅："+hallId+",座位号="+seatName;
	}
}
