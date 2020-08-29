package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

//地区类
@Component
public class AreaVo implements Serializable {

	private Integer areaId; //地区id
	private String areaName; //地区名
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "编号="+areaId+",区域名称="+areaName;
	}
}
