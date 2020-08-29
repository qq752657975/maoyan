package com.ygb.service;

import com.ygb.entity.*;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface IMoiveService {

    List<MovieVo> list(MovieVo movie, PageObject pager);

    int count(MovieVo movie);

    List<AreaVo> areaList();

    List<MovieYearVo> yearList();

    List<MovieTypeVo> movieTypeList();

    boolean add(MovieVo movie);

    boolean update(MovieVo movie);

    MovieVo getById(int movieId);

    boolean del(String[] ids);


    List<MovieVo> movieList();

    List<MovieVo> movieLoveList();

    List<MovieVo> movieLoveList1();

    List<MovieVo> movieLoveList2();

    List<ActorVo> majorActor(int movieId);

    MovieVo movieInfo(int movieId);

    List<ForumVo> byForum(int movieId,PageObject pageObject);

    Set<AgentInfo> movieAgent(PlanVo planVo, PageObject pageObject);

    List<MovieVo> agentMovieList(int agentId);

    List<PlanVo> moviePlanList(PlanVo planVo,PageObject pageObject);
}
