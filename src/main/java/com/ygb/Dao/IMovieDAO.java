package com.ygb.Dao;


import com.ygb.entity.*;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IMovieDAO {

    List<MovieVo> list(@Param("movie") MovieVo movie, @Param("pager") PageObject pager);

    int count(@Param("movie") MovieVo movie);

    List<AreaVo> areaList();

    List<MovieYearVo> yearList();

    List<MovieTypeVo> movieTypeList();

    int add(MovieVo movie);

    int update(MovieVo movie);

    MovieVo getById(int movieId);

    int del(String[] ids);

    //前端首页
    List<MovieVo> movieList();

    //前端首页
    List<MovieVo> movieList1(@Param("playType") int playType);

    //前端首页,最受期待
    List<MovieVo> movieLoveList(@Param("page") int page);

    //前端首页,评分排名
    List<MovieVo> movieForum(@Param("page") int page);

    //前端电影列表
    List<MovieVo> movieListByCondition(@Param("movie") MovieVo movie);

    //电影演员列表
    List<MovieActorVo> movieActor(@Param("movieId") int movieId);

    //评分列表
    List<ForumVo> forumList(@Param("movieId") int movieId);

    //电影明细
    MovieVo movieDetailById(@Param("movieId") int movieId);

    //增加想看人数
    void hopeLook(@Param("movieId") int movieId);

    //影片主演
    List<ActorVo> majorActor(@Param("movieId") int movieId);

    List<MovieVo> movieListByName(@Param("movieName") String movieName);

    List<MovieVo> movieLoveList1();

    List<MovieVo> movieLoveList2();

    MovieVo movieInfo(int movieId);

    List<ForumVo> byForum(@Param("movieId") int movieId,@Param("pager") PageObject pageObject);

    Set<AgentInfo> movieAgent(@Param("play") PlanVo planVo, @Param("pager") PageObject pageObject);

    List<MovieVo> agentMovieList(int agentId);

    List<PlanVo> moviePlanList(@Param("play") PlanVo planVo,@Param("pager") PageObject pageObject);


}