package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/*
	影院类
 */
@Component
public class AgentInfo implements Serializable{
	private Integer agentId;  //id
	private String agentName;  //名字
	private String agentIcon; //图片
	private String carPark; //停车场服务
	private String glasses; //3D眼镜服务
	private String children; //儿童优惠
	private int invalid; //状态
	private String contact; //联系人
	private String mobile; //联系电话
	private String address; //地址
	private String registerTime; //入驻时间
	private Integer provinceId; //省份id
	private Integer cityId;  //城市id
	private String provinceName;  //省份名称
	private String cityName;  //城市名字
	private Set<MovieVo> movieVoList;

	public Set<MovieVo> getMovieVoList() {
		return movieVoList;
	}

	public void setMovieVoList(Set<MovieVo> movieVoList) {
		this.movieVoList = movieVoList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AgentInfo agentInfo = (AgentInfo) o;
		return invalid == agentInfo.invalid;

	}

	@Override
	public int hashCode() {
		return Objects.hash(agentId, agentName, agentIcon, carPark, glasses, children, invalid, contact, mobile, address, registerTime, provinceId, cityId, provinceName, cityName);
	}

	@Override
	public String toString() {
		return "影院id:"+agentId+",影院名称:"+agentName+",联系人："+contact+",电话:"+mobile+",地址:"+address;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {

		this.agentName = agentName;
	}

	public String getAgentIcon() {
		return agentIcon;

	}

	public void setAgentIcon(String agentIcon) {
		this.agentIcon = agentIcon;
	}

	public String getCarPark() {
		return carPark;
	}

	public void setCarPark(String carPark) {
		this.carPark = carPark;
	}

	public String getGlasses() {
		return glasses;
	}

	public void setGlasses(String glasses) {
		this.glasses = glasses;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public int getInvalid() {
		return invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



}
