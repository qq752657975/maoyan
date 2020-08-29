package com.ygb.Controller;


import com.ygb.entity.*;
import com.ygb.service.IMoiveService;
import com.ygb.service.IOrderService;
import com.ygb.service.IPlanService;
import com.ygb.util.ContextUtils;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    IOrderService iOrderService;
    @Resource
    IMoiveService iMoiveService;
    @Resource
    ResultInfo resultInfo;
    //票房统计
    @RequestMapping("/movieReport")
    public ResultInfo movieReport(PlanVo plan,PageObject pager){

        List<MovieVo> movieList = iMoiveService.movieList();
        List<OrderVo> orderList = iOrderService.movieRep(plan,pager);
        pager.setDatas(orderList);
        Map<String,Object> map = new HashMap<>();
        map.put("movieList",movieList);
        map.put("pager",pager);
        map.put("plan",plan);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setData(map);
        return resultInfo;
    }
    //影院统计
    @RequestMapping("/agentReport")
    public ResultInfo agentReport(PlanVo plan,PageObject pager){
        Manager mgr = ContextUtils.getUserInfo();
        int agentId = 0;
        if(mgr.getManagerType() !=3){
            agentId=mgr.getAgentId();
        }
        List<AgentInfo> agentList = iOrderService.agentList(agentId);
        List<OrderVo> orderList = iOrderService.agentRep(plan,pager);
        pager.setDatas(orderList);
        Map<String,Object> map = new HashMap<>();
        map.put("agentList",agentList);
        map.put("pager",pager);
        map.put("plan",plan);
        resultInfo.setData(map);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }
    //影厅统计
    @RequestMapping("/hallReport")
    public ResultInfo hallReport(PlanVo plan,PageObject pager){
        Manager mgr = ContextUtils.getUserInfo();
        int agentId = 0;
        if(mgr.getManagerType() !=3){
            agentId=mgr.getAgentId();
        }
        List<AgentInfo> agentList = iOrderService.agentList(agentId);
        List<OrderVo> orderList = iOrderService.hallRep(plan,pager);
        Map<String,Object> map = new HashMap<>();
        pager.setDatas(orderList);
        map.put("agentList",agentList);
        map.put("pager",pager);
        map.put("plan",plan);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setData(map);
        return resultInfo;
    }

}
