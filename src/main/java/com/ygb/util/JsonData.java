package com.ygb.util;

import org.springframework.stereotype.Component;

//公共类
@Component
public class JsonData {

	private String id; //编号
	private String name; //名称
	private boolean selected; //是否选中
	public JsonData() {

	}
	public JsonData(String id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
