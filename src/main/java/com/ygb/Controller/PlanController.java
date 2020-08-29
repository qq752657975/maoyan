package com.ygb.Controller;

import com.ygb.entity.*;
import com.ygb.service.IAgentInfoService;
import com.ygb.service.IPlanService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.ContextUtils;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Resource
    ResultInfo resultInfo;

    @Resource
    IPlanService iPlanService;

    @Resource
    ISysLogService iSysLogService;


    //排片列表条件
    @RequestMapping("/list")
    public ResultInfo list(PlanVo plan , PageObject pager, HttpSession session){
        session.setAttribute("plan",plan);
        Map params = getParams(plan, pager);
        resultInfo.setData(params);
        resultInfo.setFlag(true);
        resultInfo.setState(200);

        resultInfo.setErrorMsg("查询成功");
        return resultInfo;
    }
    //按条件查询排片列表
    private Map getParams(PlanVo plan , PageObject pager){
        Manager mgr = ContextUtils.getUserInfo();

         plan.setAgentId(mgr.getAgentId());

        int cnt = iPlanService.count(plan);
        pager.setTotalRows(cnt);
        //查询所有的排片列表
        List<PlanVo> planList = iPlanService.list(plan,pager);
        pager.setDatas(planList);
        //查询所有的电影列表
        List<MovieVo> movieList = iPlanService.movieList();
        //查询符合id的影厅
        List<MovieHallVo> hallList = iPlanService.hallList(plan.getAgentId());

//        List<AgentInfo> agentList = iPlanService.agentList(plan.getAgentId());
        //查询所有的排片时间
        List<PlayTimeVo> playTimeList = iPlanService.playTimeList();
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("plan",plan);
        map.put("movieList",movieList);
        map.put("hallList",hallList);
//        map.put("agentList",agentList);
        map.put("playTimeList",playTimeList);
        return map;
    }

    //添加页面回显
    @RequestMapping("/add")
    public ResultInfo add(){
        PlanVo plan = new PlanVo();
        Manager mgr = ContextUtils.getUserInfo();
//        if(mgr.getManagerType()!=3){
        plan.setAgentId(mgr.getAgentId());
//        }
        //查询所有的电影
        List<MovieVo> movieList = iPlanService.movieList();
        //查询id查询符合条件的影厅
        List<MovieHallVo> hallList = iPlanService.hallList(plan.getAgentId());
//        List<AgentInfo> agentList = planService.agentList(plan.getAgentId());
        //查询所有的排片时间
        List<PlayTimeVo> playTimeList = iPlanService.playTimeList();
        Map<String,Object> map = new HashMap<>();
        map.put("plan",plan);
        map.put("movieList",movieList);
        map.put("hallList",hallList);
//        map.addAttribute("agentList",agentList);
        map.put("playTimeList",playTimeList);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }

    //修改页面回显
    @RequestMapping("/update")
    public ResultInfo update(PlanVo plan){
        //通过排片id 获取排片信息
        plan = iPlanService.getById(plan.getPlanId());
        Manager mgr = ContextUtils.getUserInfo();
        int agentId=0;
//        if(mgr.getManagerType()!=3){
        agentId=mgr.getAgentId();
//        }
        //查询所有的电影
        List<MovieVo> movieList = iPlanService.movieList();
        //查询该影院的影厅
        List<MovieHallVo> hallList = iPlanService.hallList(plan.getAgentId());

//        List<AgentInfo> agentList = planService.agentList(agentId);
        //查询所有的排片时间
        List<PlayTimeVo> playTimeList = iPlanService.playTimeList();
        Map<String,Object> map = new HashMap<>();
        map.put("plan",plan);
        map.put("movieList",movieList);
        map.put("hallList",hallList);
//        map.addAttribute("agentList",agentList);
        map.put("playTimeList",playTimeList);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }

    //删除排片
    @RequestMapping("/del")
    public ResultInfo del(String ids,PageObject pageObject,HttpSession session){
        String [] arr = ids.split(",");
        if(iPlanService.del(arr)){
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            //查询删除后的数据，用于更新页面信息
            PlanVo plan = (PlanVo) session.getAttribute("plan");
            Map params = getParams(plan,pageObject);
            resultInfo.setData(params);
            resultInfo.setErrorMsg("删除成功");
            return resultInfo;
        }
        resultInfo.setErrorMsg("删除失败");
        resultInfo.setFlag(false);
        resultInfo.setState(201);
        return resultInfo;
    }
    //新增或修改操作
    @PostMapping("/save")
    public ResultInfo save(HttpSession session,PlanVo plan,PageObject pageObject){
        Manager mgr = ContextUtils.getUserInfo();
        plan.setAgentId(mgr.getAgentId());



        if(plan.getPlanId()>0){ //如果传入id则表示修改操作
            if(iPlanService.update(plan)){
                iSysLogService.add(BaseConstants.LOG_UPDATE,plan.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
                //把对象赋空
                plan = new PlanVo();
                //查询session中的条件取出
                if(session.getAttribute("plan")!=null){
                    plan  = (PlanVo) session.getAttribute("plan");
                }
                //查询修改后数据，用于更新页面数据
                Map params = getParams(plan, pageObject);
                resultInfo.setData(params);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
            }
        }else { //如果没有传入id则表示新增操作
            if(iPlanService.add(plan)){
                iSysLogService.add(BaseConstants.LOG_ADD,plan.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("新增成功");
                PlanVo planVo1 = (PlanVo) session.getAttribute("plan");

                //查询新增后数据，用于更新页面数据
                Map params = getParams(planVo1,pageObject);
                resultInfo.setData(params);
            }
        }
        return resultInfo;
    }
}
