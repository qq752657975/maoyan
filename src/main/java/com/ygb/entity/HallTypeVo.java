package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//放映厅类型
@Component
public class HallTypeVo implements Serializable {

	private Integer hallTypeId; //id
	private String hallTypeName; //放映厅类型

	public Integer getHallTypeId() {
		return hallTypeId;
	}

	public void setHallTypeId(Integer hallTypeId) {
		this.hallTypeId = hallTypeId;
	}

	public String getHallTypeName() {
		return hallTypeName;
	}

	public void setHallTypeName(String hallTypeName) {
		this.hallTypeName = hallTypeName;
	}

	@Override
	public String toString() {
		return "编号="+hallTypeId+",影厅类别名称="+hallTypeName;
	}
}
