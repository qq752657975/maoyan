package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

//城市类
@Component
public class CityVo implements Serializable {
	private long cityId;  //城市id
	private String cityName; //城市名字
	private long seq; //排序
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}