package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//电影类别
@Component
public class MovieTypeVo implements Serializable {

	private Integer movieTypeId; //编号
	private String movieTypeName; //类别名称

	public Integer getMovieTypeId() {
		return movieTypeId;
	}

	public void setMovieTypeId(Integer movieTypeId) {
		this.movieTypeId = movieTypeId;
	}

	public String getMovieTypeName() {
		return movieTypeName;
	}

	public void setMovieTypeName(String movieTypeName) {
		this.movieTypeName = movieTypeName;
	}

	@Override
	public String toString() {
		return "编号="+movieTypeId+",类别名称="+movieTypeName;
	}
}
