package com.ygb.service.Impl;

import com.ygb.Dao.IAgentDao;
import com.ygb.entity.AgentInfo;
import com.ygb.entity.CityVo;
import com.ygb.entity.PlanVo;
import com.ygb.entity.ProvinceVo;
import com.ygb.service.IAgentInfoService;
import com.ygb.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentInfoServiceImpl implements IAgentInfoService {
    @Autowired
    IAgentDao iAgentDao;

    @Override
    public List<AgentInfo> list(AgentInfo agentInfo, PageObject pageObject) {
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
            return iAgentDao.list(agentInfo,pageObject);

    }

    @Override
    public int count(AgentInfo agentInfo){
        return iAgentDao.count(agentInfo);
    }

    @Override
    public List<ProvinceVo> province() {
        return iAgentDao.province();
    }

    @Override
    public List<CityVo> city(int provinceId) {
        if(provinceId>0){
            return iAgentDao.city(provinceId);
        }else {
            return null;
        }


    }

    @Override
    public int update(AgentInfo agentInfo) {

            return iAgentDao.update(agentInfo);
    }

    @Override
    public int add(AgentInfo agentInfo) {

        return iAgentDao.add(agentInfo);
    }

    @Override
    public AgentInfo getById(int agentId) {

        return iAgentDao.getById(agentId);
    }

    @Override
    public int del(String[] ids) {

            return iAgentDao.del(ids);


    }

    @Override
    public PlanVo getPlanById(int planId) {
        return iAgentDao.getPlanById(planId);
    }
}
