package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//影片
@Component
public class MovieVo implements Serializable {

	private Integer movieId; //编号
	private String movieName; //电影名
	private int movieTypeId;//电影类别：如战争，爱情，历史等
	private int movieYearId; //拍摄年份
	private int movieAreaId; //所属区域，如大陆，美国等
	private int playType; //1即将上映，2正在热映，3经典影片
	private int hopeLook;  //想看人数
	private int seq;  //排序
	private String cimg; //电影图片
	private String registerTime; //上映时间
	private String content; //满意度
	private String areaName; //地区名
	private String movieYearName; //
	private String movieTypeName;
	private List<ForumVo> forumVos;
	private String remark; //备注
	private List<ActorVo> actorName;
	private List<PlanVo> planVos;
	//电影评分
	private float score;
	//票房统计
	private float saleTotal;

	public List<PlanVo> getPlanVos() {
		return planVos;
	}

	public void setPlanVos(List<PlanVo> planVos) {
		this.planVos = planVos;
	}

	public MovieVo() {
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ActorVo> getActorName() {
		return actorName;
	}

	public List<ForumVo> getForumVos() {
		return forumVos;
	}

	public void setForumVos(List<ForumVo> forumVos) {
		this.forumVos = forumVos;
	}

	public void setActorName(List<ActorVo> actorName) {
		this.actorName = actorName;
	}
	public float getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(float saleTotal) {
		this.saleTotal = saleTotal;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMovieYearName() {
		return movieYearName;
	}

	public void setMovieYearName(String movieYearName) {
		this.movieYearName = movieYearName;
	}

	public String getMovieTypeName() {
		return movieTypeName;
	}

	public void setMovieTypeName(String movieTypeName) {
		this.movieTypeName = movieTypeName;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMovieTypeId() {
		return movieTypeId;
	}

	public void setMovieTypeId(int movieTypeId) {
		this.movieTypeId = movieTypeId;
	}

	public int getMovieYearId() {
		return movieYearId;
	}

	public void setMovieYearId(int movieYearId) {
		this.movieYearId = movieYearId;
	}

	public int getMovieAreaId() {
		return movieAreaId;
	}

	public void setMovieAreaId(int movieAreaId) {
		this.movieAreaId = movieAreaId;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public int getHopeLook() {
		return hopeLook;
	}

	public void setHopeLook(int hopeLook) {
		this.hopeLook = hopeLook;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCimg() {
		return cimg;
	}

	public void setCimg(String cimg) {
		this.cimg = cimg;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MovieVo movieVo = (MovieVo) o;
		return movieId == movieVo.movieId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(movieId, movieName, movieTypeId, movieYearId, movieAreaId, playType, hopeLook, seq, cimg, registerTime, content, areaName, movieYearName, movieTypeName, forumVos, actorName, score, saleTotal);
	}






	@Override
	public String toString() {
		return "编号="+movieId+",电影名称="+movieName;
	}
}
