package com.ygb.Controller;

import com.ygb.entity.ActorVo;
import com.ygb.entity.MovieActorVo;
import com.ygb.service.IMovieActorService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movieActor")
public class MovieActorController {

    @Resource
    IMovieActorService iMovieActorService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    ISysLogService iSysLogService;

    //查询演员类别
    @RequestMapping("/list")
    @Transactional
    public ResultInfo list(int movieId , PageObject pager,HttpSession session){
        //存储查找演员条件的id
        session.setAttribute("movieId",movieId);

        //根据条件查找演员列表
        Map params = getParams(movieId, pager);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("查找成功");
        resultInfo.setData(params);
        return resultInfo;
    }
    //根据条件查找演员列表
    private Map getParams(int movieId , PageObject pager){
        int cnt = iMovieActorService.count(movieId);
        pager.setTotalRows(cnt);
        List<MovieActorVo> movieList = iMovieActorService.list(movieId,pager);
        System.out.println(movieList);
        pager.setDatas(movieList);
        List<ActorVo> actorList = iMovieActorService.actorList();
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("actorList",actorList);
        map.put("movieActor",new MovieActorVo().getTypeName());
        return map;
    }

    //修改或者添加演员
    @PostMapping("/save")
    public ResultInfo save(int movieId,MovieActorVo movieActor,PageObject pageObject){

        movieActor.setMovieId(movieId);
        Map<String,Object> map;
        if(movieActor.getMovieActorId()>0){ //如果传入id表示为修改操作
            if(iMovieActorService.update(movieActor)){
                iSysLogService.add(BaseConstants.LOG_UPDATE,movieActor.toString());
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                //查询修改后的数据，更新前台数据
                map = getParams(movieId,pageObject);
                resultInfo.setData(map);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
            }
        }else {//没有id则为新增操作
            if(iMovieActorService.add(movieActor)){
              iSysLogService.add(BaseConstants.LOG_ADD,movieActor.toString());
                resultInfo.setErrorMsg("添加成功");
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                //查询修改后的数据，更新前台数据
                map = getParams(movieId,pageObject);
                resultInfo.setData(map);
            }else{
            resultInfo.setFlag(false);
            resultInfo.setState(201);
            resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }

    //添加页面回显
    @RequestMapping("/add")
    public ResultInfo add(){
        List<ActorVo> actorList = iMovieActorService.actorList();
        Map<String,Object> map = new HashMap<>();
        map.put("actorList",actorList);
        map.put("movieActor",new MovieActorVo());
        resultInfo.setData(map);
        return resultInfo;
    }

    //修改页面回显
    @RequestMapping("/update")
    public ResultInfo update(MovieActorVo movieActor){
        movieActor = iMovieActorService.getById(movieActor.getMovieActorId());
        List<ActorVo> actorList = iMovieActorService.actorList();
        Map<String,Object> map = new HashMap<>();
        map.put("actorList",actorList);
        map.put("movieActor",movieActor);
        return resultInfo;
    }

    //删除演员
    @RequestMapping("/del")
    public ResultInfo del(String ids,HttpSession session,PageObject pageObject){
        //保存查询条件
        int movieId  = (Integer) session.getAttribute("movieId");

        String [] arr = ids.split(",");
        if(iMovieActorService.del(arr)){
            //查询删除后的演员信息，并保留查询条件，更新页面数据
            Map params = getParams(movieId,pageObject);
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setData(params);
        }

        return resultInfo;
    }
}
