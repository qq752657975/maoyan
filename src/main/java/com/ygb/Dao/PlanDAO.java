package com.ygb.Dao;


import com.ygb.entity.*;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDAO {

    PlanVo getById(int planId);
    List<PlanVo> list(@Param("plan") PlanVo plan, @Param("pager") PageObject pager);
    int count(@Param("plan") PlanVo plan);

    int del(String[] ids);

    int add(PlanVo plan);

    int update(PlanVo plan);

    List<MovieVo> movieList();

    List<MovieHallVo> hallList(@Param("agentId") int agentId);

    List<AgentInfo> agentList(@Param("agentId") int agentId);

    List<PlayTimeVo> playTimeList();

    List<AgentInfo> AgentList();


}
