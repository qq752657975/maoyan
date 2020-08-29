package com.ygb.service;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.CityVo;
import com.ygb.entity.PlanVo;
import com.ygb.entity.ProvinceVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAgentInfoService {
    public List<AgentInfo> list(@Param("agent") AgentInfo agentInfo, PageObject pageObject);

    int count(AgentInfo agentInfo);

    List<ProvinceVo> province();

    List<CityVo> city(int provinceId);

    int update(AgentInfo agentInfo);

    int add(AgentInfo agentInfo);

    AgentInfo getById(int agentId);

    int del(String[] ids);

    PlanVo getPlanById(int planId);

}
