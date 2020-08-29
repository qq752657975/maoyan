package com.ygb.service;

import com.ygb.entity.*;
import com.ygb.util.PageObject;

import java.util.List;

public interface IPlanService {

    PlanVo getById(int planId);

    List<PlanVo> list(PlanVo plan, PageObject pager);

    int count(PlanVo plan);

    boolean del(String[] arr);

    boolean add(PlanVo plan);

    boolean update(PlanVo plan);

    List<MovieVo> movieList();

    List<MovieHallVo> hallList(int agentId);

    List<AgentInfo> agentList(int agentId);

    List<PlayTimeVo> playTimeList();



}
