package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

//演员信息
@Component
public class ActorVo implements Serializable {

	private Integer actorId;  //id
	private String actorName; //名字
	private String cimg; //图片地址

	public Integer getActorId() {
		return actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getCimg() {
		return cimg;
	}

	public void setCimg(String cimg) {
		this.cimg = cimg;
	}

	@Override
	public String toString() {
		return "编号="+actorId+",演员姓名="+actorName;
	}
}
