package com.ygb.Controller;

import com.ygb.entity.ForumVo;
import com.ygb.entity.Manager;
import com.ygb.entity.UserVo;
import com.ygb.service.IForumSerivce;
import com.ygb.service.ISysLogService;
import com.ygb.service.IUserService;
import com.ygb.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Resource
    ResultInfo resultInfo;

    @Resource
    ISysLogService logService;

    @Resource
    IUserService userService;

    @Resource
    IForumSerivce iForumSerivce;

    @RequestMapping("/list")
    public ResultInfo list(int movieId , PageObject pager, HttpSession session){
        //获取电影的id
        session.setAttribute("movieId",movieId);
        Map params = getParams(movieId, pager);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setState(200);
        resultInfo.setData(params);
        return resultInfo;
    }
    private Map getParams(int movieId , PageObject pager){
        //根据电影名查询评论的总条数
        int cnt = iForumSerivce.count(movieId);
        pager.setTotalRows(cnt);
        //根据条件和分页查询评论
        List<ForumVo> movieList = iForumSerivce.list(movieId,pager);
        pager.setDatas(movieList);
        //将查询后的数据保存到map中
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("forum",new ForumVo());
        return map;
    }
    //修改评论或新增评论
    @PostMapping("/save")
    public ResultInfo save(ForumVo forum,int movieId,PageObject pageObject){

        UserVo u=null;
        //如果有用户的手机号则查询该用户的信息
        if(forum.getMobile()!=null) {
            u = userService.getById(forum.getMobile());
        }
        //片段评论者是否登录，没有登录不能评论
        if(u==null){
            resultInfo.setErrorMsg("该手机号码不是注册用户，评论失败。");
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        Map<String,Object> map;
        forum.setMovieId(movieId);
        if(forum.getForumId()>0){ //如果传入电影的id则表示修改
            if(iForumSerivce.update(forum)){
                logService.add(BaseConstants.LOG_UPDATE,forum.toString());
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                map = getParams(movieId,pageObject);
                resultInfo.setData(map);
            }else{
                resultInfo.setErrorMsg("修改失败");
                resultInfo.setFlag(false);
                resultInfo.setState(201);
            }
        }else {//如果没有传入电影的id表示新增
            if(iForumSerivce.add(forum)){
                logService.add(BaseConstants.LOG_ADD,forum.toString());
                resultInfo.setErrorMsg("添加成功");
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                map = getParams(movieId,pageObject);
                resultInfo.setData(map);
            }else{
                resultInfo.setErrorMsg("添加失败");
                resultInfo.setFlag(false);
                resultInfo.setState(201);
            }
        }
        return resultInfo;
    }
    //修改页面的回显
    @RequestMapping("/update")
    public ResultInfo update(ForumVo forum){
        //查询修改前的数据，封装到前台显示
        forum = iForumSerivce.getById(forum.getForumId());
        resultInfo.setData(forum);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }
    //删除评论
    @RequestMapping("/del")
    public ResultInfo del(String ids,int movieId){
        String [] arr = ids.split(",");
        if(iForumSerivce.del(arr)){
            //删除完之后，查询数据，更新页面数据
            Map params = getParams(movieId, new PageObject());
            resultInfo.setData(params);
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            resultInfo.setErrorMsg("删除成功");
        }else{
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");
        }

        return resultInfo;
    }
}
