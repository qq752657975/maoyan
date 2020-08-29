package com.ygb.Controller;

import com.ygb.entity.MovieYearVo;
import com.ygb.service.IMoveYearService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/movieYear")
public class MovieYearController {
    @Resource
    ISysLogService logService;

    @Resource
    IMoveYearService iMoveYearService;

    @Resource
    ResultInfo resultInfo;

    //显示所有年份列表
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager){
        int cnt = iMoveYearService.count();
        pager.setTotalRows(cnt);
        List<MovieYearVo> areaList = iMoveYearService.list(pager);
        pager.setDatas(areaList);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setData(pager);
        return resultInfo;
    }
    //删除年份
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){
        int cnt = 0;
        String arr[] = ids.split(",");
        if(iMoveYearService.del(arr)){
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的年代");
            cnt = iMoveYearService.count();
            pager.setTotalRows(cnt);
            List<MovieYearVo> areaList = iMoveYearService.list(pager);
            pager.setDatas(areaList);
            resultInfo.setData(pager);
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("查询成功");
            return resultInfo;
        }
        resultInfo.setFlag(false);
        resultInfo.setErrorMsg("查询失败");
        resultInfo.setState(201);
        return resultInfo;
    }
    //用于修改之前的回显操作
    @RequestMapping("/update")
    public ResultInfo update(int movieYearId){
        MovieYearVo movieYear = iMoveYearService.getById(movieYearId);
        resultInfo.setData(movieYear);
        return resultInfo;
    }
    //修改或者新增操作
    @RequestMapping("/save")
    public ResultInfo save(MovieYearVo movieYear,PageObject pager){
        int cnt = 0;
        if(movieYear.getMovieYearId()>0){ //如果传入id 则表示修改操作
           if(iMoveYearService.update(movieYear)){
               logService.add(BaseConstants.LOG_UPDATE,movieYear.toString());
               resultInfo.setState(200);
               resultInfo.setFlag(true);
               resultInfo.setErrorMsg("修改成功");
               //查询修改后的信息，更新页面数据
               cnt = iMoveYearService.count();
               pager.setTotalRows(cnt);
               List<MovieYearVo> areaList = iMoveYearService.list(pager);
               pager.setDatas(areaList);
               resultInfo.setData(pager);
           }else{
               resultInfo.setState(201);
               resultInfo.setFlag(false);
               resultInfo.setErrorMsg("修改失败");
           }
        }else {
            if(iMoveYearService.add(movieYear)){
                logService.add(BaseConstants.LOG_ADD,movieYear.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
                //查询修改后的信息，更新页面数据
                cnt = iMoveYearService.count();
                pager.setTotalRows(cnt);
                List<MovieYearVo> areaList = iMoveYearService.list(pager);
                pager.setDatas(areaList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }
}
