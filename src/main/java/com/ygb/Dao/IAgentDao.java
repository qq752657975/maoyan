package com.ygb.Dao;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.CityVo;
import com.ygb.entity.PlanVo;
import com.ygb.entity.ProvinceVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAgentDao {

    int count(@Param("agent") AgentInfo agentInfo);

    List<AgentInfo> list(@Param("agent") AgentInfo agentInfo, @Param("pager")PageObject pageObject);

    List<ProvinceVo> province();

    List<CityVo> city(int provinceId);

    int update(AgentInfo agentInfo);

    int add(AgentInfo agentInfo);

    AgentInfo getById(int agentId);

    int del(String[] ids);

    PlanVo getPlanById(int planId);

}
