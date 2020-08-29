package com.ygb.entity;

import com.ygb.entity.CityVo;
import org.springframework.stereotype.Component;

import java.util.List;

//省份
@Component
public class ProvinceVo implements java.io.Serializable {

	private int provinceId; //编号
	private String provinceName; //省份名
	private int seq; //排序
	private List<CityVo> cityList;  //城市

	public List<CityVo> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityVo> cityList) {
		this.cityList = cityList;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}