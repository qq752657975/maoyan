package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//电影发布年份
@Component
public class MovieYearVo implements Serializable {

	private Integer movieYearId; //编号

	private String movieYearName; //年份名

	public Integer getMovieYearId() {
		return movieYearId;
	}

	public void setMovieYearId(Integer movieYearId) {
		this.movieYearId = movieYearId;
	}

	public String getMovieYearName() {
		return movieYearName;
	}

	public void setMovieYearName(String movieYearName) {
		this.movieYearName = movieYearName;
	}

	@Override
	public String toString() {
		return "编号="+movieYearId+",年代="+movieYearName;
	}
}
