package com.ygb.service.Impl;

import com.ygb.Dao.PlanDAO;
import com.ygb.entity.*;
import com.ygb.service.IPlanService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlanServiceImpl implements IPlanService {

    @Resource
    PlanDAO planDAO;

    @Override
    public PlanVo getById(int planId) {
        return planDAO.getById(planId);
    }

    @Override
    public List<PlanVo> list(PlanVo plan, PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }

        return planDAO.list(plan, pager);
    }

    @Override
    public int count(PlanVo plan) {
        return planDAO.count(plan);
    }

    @Override
    public boolean del(String[] arr) {
        if(arr != null && arr.length>0){
            int del = planDAO.del(arr);
            if(del > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(PlanVo plan) {
        if(plan != null){
            int add = planDAO.add(plan);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(PlanVo plan) {
        if(plan != null){
            int update = planDAO.update(plan);
            if(update > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MovieVo> movieList() {
         return planDAO.movieList();
    }

    @Override
    public List<MovieHallVo> hallList(int agentId) {

        return planDAO.hallList(agentId);
    }

    @Override
    public List<AgentInfo> agentList(int agentId) {

        return planDAO.agentList(agentId);
    }

    @Override
    public List<PlayTimeVo> playTimeList() {

        return planDAO.playTimeList();
    }
}
