package com.ygb.Controller;

import com.ygb.entity.*;
import com.ygb.service.IMovieHallService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.ContextUtils;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movieHall")
public class MovieHallController {
    @Resource
    ISysLogService logService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    IMovieHallService movieHallService;

    //显示所有的影厅信息
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager, MovieHallVo movieHall, HttpSession session){
        //判断是否有查询条件，用于分页中的条件

       session.setAttribute("movieHall",movieHall);

        Manager mgr = ContextUtils.getUserInfo();
        //如果不是系统管理员，则只能看到自己影院的信息
        List<AgentInfo> agentList = new ArrayList<>();
        if(mgr.getManagerType()!=3){
            movieHall.setAgentId(mgr.getAgentId());
            agentList = movieHallService.agentList(movieHall.getAgentId());
        }else{
            agentList = movieHallService.agentList(0);
        }
        //查询符合条件的影院

        int cnt = movieHallService.count(movieHall);
        pager.setTotalRows(cnt);
        //查询符合条件的影厅
        List<MovieHallVo> movieHallList = movieHallService.list(movieHall,pager);
        List<HallTypeVo> hallTypeList = movieHallService.hallTypeList();
        pager.setDatas(movieHallList);
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("movieHall",movieHall);
        map.put("agentList",agentList);
        map.put("hallTypeList",hallTypeList);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setData(map);
        return resultInfo;
    }

    //添加页面的回显
    @RequestMapping("/init")
    public ResultInfo init(){
        //获取session存在对象
        Manager mgr = ContextUtils.getUserInfo();

        List<AgentInfo> agentList =null;
        //判断用户的身份，只能有对应身份的影院
        if(mgr.getManagerType()!=3){
            agentList = movieHallService.agentList(mgr.getAgentId());
        }else{
            agentList = movieHallService.agentList(0);
        }
        MovieHallVo hall = new MovieHallVo();
        //设置状态为可用状态
        hall.setState(1);
        //查询影院类型信息
        List<HallTypeVo> hallTypeList = movieHallService.hallTypeList();
        Map<String,Object> map = new HashMap<>();
        map.put("movieHall",hall);
        map.put("agentList",agentList);
        map.put("hallTypeList",hallTypeList);
        resultInfo.setData(map);
        resultInfo.setState(200);
        return resultInfo;
    }

    //新增或修改
    @RequestMapping("/save")
    public ResultInfo save(MovieHallVo movieHall,HttpSession session,PageObject pager){
        int cnt = 0;
        MovieHallVo movieHall1 = (MovieHallVo) session.getAttribute("movieHall");
        if(movieHall.getHallId()>0){ //传入id 修改操作
            if(movieHallService.update(movieHall)){
                logService.add(BaseConstants.LOG_UPDATE,movieHall.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("修改成功");
                //查询修改之后的数据，更新页面数据
                cnt =  movieHallService.count(movieHall1);
                List<MovieHallVo> movieHallList = movieHallService.list(movieHall1,pager);
                pager.setTotalRows(cnt);
                pager.setDatas(movieHallList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("修改失败");
            }
        }else { //没有id 则是新增操作
            if(movieHallService.add(movieHall)){
                logService.add(BaseConstants.LOG_ADD,movieHall.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
                //查询新增之后的数据，更新页面数据
                cnt =  movieHallService.count(movieHall1);
                List<MovieHallVo> movieHallList = movieHallService.list(movieHall1,pager);
                pager.setTotalRows(cnt);
                pager.setDatas(movieHallList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("修改失败");
            }

        }
        return resultInfo;
    }
    //修改页面回显
    @RequestMapping("/update")
    public ResultInfo update(int hallId){
        //获取用户对象
        Manager mgr = ContextUtils.getUserInfo();
        List<AgentInfo> agentList =null;
        //判断用户身份，显示相应的操作
        if(mgr.getManagerType()!=3){
            agentList = movieHallService.agentList(mgr.getAgentId());
        }else{
            agentList = movieHallService.agentList(0);
        }
        //查询显示页面的信息
        List<HallTypeVo> hallTypeList = movieHallService.hallTypeList();
        MovieHallVo movieHall = movieHallService.getById(hallId);
        Map<String,Object> map = new HashMap<>();
        map.put("movieHall",movieHall);
        map.put("agentList",agentList);
        map.put("hallTypeList",hallTypeList);
        resultInfo.setData(map);
        return resultInfo;
    }

    //删除影厅
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager,HttpSession session){
    int cnt = 0;
        MovieHallVo movieHall = new MovieHallVo();
        //判断session中是否存在查询条件
        if(session.getAttribute("movieHall") !=null){
            movieHall = (MovieHallVo)session.getAttribute("movieHall");
        }
        String arr[] = ids.split(",");
        if(movieHallService.del(arr)){
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的影厅");
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setFlag(true);
            resultInfo.setState(200);
           cnt =  movieHallService.count(movieHall);
           List<MovieHallVo> movieHallList = movieHallService.list(movieHall,pager);
           pager.setTotalRows(cnt);
           pager.setDatas(movieHallList);
           resultInfo.setData(pager);
        }else{
            resultInfo.setErrorMsg("删除失败");
            resultInfo.setFlag(false);
            resultInfo.setState(201);
            return resultInfo;
        }
        return resultInfo;
    }

    //生成座位
    @RequestMapping("/seat")
    public ResultInfo seat(MovieHallVo hall){
        //查询座位的排数和列数
        hall = movieHallService.getById(hall.getHallId());
        //判断是否有座位数
        if(hall.getSeatNum()==0) {
            //如果没有座位数，调用seatAdd方法生成座位数
            movieHallService.seatAdd(hall.getHallId());
        }
        //查询所有座位
        List<HallSeatVo> seatList = movieHallService.getSeatByHallId(hall.getHallId());
        Map<String,Object> map = new HashMap<>();
        resultInfo.setState(200);
        map.put("hall",hall);
        map.put("seatList",seatList);
        resultInfo.setData(map);
        return resultInfo;
    }


    //设置座位状态
    @RequestMapping("/state")
    public ResultInfo state(HallSeatVo seat,PageObject pager, MovieHallVo movieHall){
        if(movieHallService.seatUpdate(seat)){
            resultInfo.setState(200);
            resultInfo.setErrorMsg("修改成功");
            resultInfo.setFlag(true);
            List<MovieHallVo> movieHallList = movieHallService.list(movieHall,pager);
            movieHall = movieHallService.getById(movieHall.getHallId());
            List<HallSeatVo> seatList = movieHallService.getSeatByHallId(movieHall.getHallId());
            Map<String,Object> map = new HashMap<>();
            map.put("hall",movieHall);
            map.put("seatList",seatList);
            map.put("movieHallList",movieHallList);
            resultInfo.setData(map);

        }else{
            resultInfo.setState(201);
            resultInfo.setErrorMsg("修改失败");
            resultInfo.setFlag(false);
        }
        return resultInfo;
    }

    //重新生成座位
    @RequestMapping("/reSeat")
    public ResultInfo reSeat(MovieHallVo hall,PageObject pageObject,HttpSession session){
        hall = movieHallService.getById(hall.getHallId());
        //删除所有的座位数
        movieHallService.seatDel(hall.getHallId());
        //生成新的座位数
        movieHallService.seatAdd(hall.getHallId());
        //查询生成后的座位数，更新页面数据
        List<HallSeatVo> seatList = movieHallService.getSeatByHallId(hall.getHallId());
        Map<String,Object> map = new HashMap<>();
        map.put("hall",hall);
        map.put("seatList",seatList);
        MovieHallVo movieHall = (MovieHallVo) session.getAttribute("movieHall");
        List<MovieHallVo> movieHallList = movieHallService.list(movieHall,pageObject);
        map.put("movieHallList",movieHallList);
        resultInfo.setState(200);
        resultInfo.setFlag(true);

        resultInfo.setData(map);
        resultInfo.setErrorMsg("生成成功");
        return resultInfo;
    }
}
