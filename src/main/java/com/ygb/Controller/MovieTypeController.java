package com.ygb.Controller;

import com.ygb.entity.MovieTypeVo;
import com.ygb.service.IMovieTypeService;
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
@RequestMapping("/movieType")
public class MovieTypeController {
    @Resource
    ISysLogService logService;

    @Resource
    IMovieTypeService iMovieService;

    @Resource
    ResultInfo resultInfo;

    //查询所有的电影类型
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager, HttpSession session){
        int cnt = iMovieService.count();
        pager.setTotalRows(cnt);
        List<MovieTypeVo> movieTypeList = iMovieService.list(pager);
        pager.setDatas(movieTypeList);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setData(pager);
        return resultInfo;
    }

    //删除电影类型
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){

        System.out.println("ids="+ids);
        String arr[] = ids.split(",");
        if(iMovieService.del(arr)) {
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的考区");
            //删除数据之后，更新数据，并更新页面
            int cnt = iMovieService.count();
            pager.setTotalRows(cnt);
            List<MovieTypeVo> areaList = iMovieService.list(pager);
            pager.setDatas(areaList);
            resultInfo.setData(pager);
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");
        }else {
            resultInfo.setErrorMsg("删除失败");
            resultInfo.setState(201);
            resultInfo.setFlag(false);
        }
        return resultInfo;
    }

    //修改的回显
    @RequestMapping("/update")
    public ResultInfo update(int movieTypeId){
        MovieTypeVo movieType = iMovieService.getById(movieTypeId);
        return resultInfo;
    }

    //修改和新增操作
    @RequestMapping("/save")
    public ResultInfo save(MovieTypeVo movieType,PageObject pager){
        int cnt = 0;

        if(movieType.getMovieTypeId()>0){ //如果传入的有id 表示修改操作
            if(iMovieService.update(movieType)){
                logService.add(BaseConstants.LOG_UPDATE,movieType.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("修改成功");
                //查询修改后的数据，更新页面数据
                cnt = iMovieService.count();
                pager.setTotalRows(cnt);
                List<MovieTypeVo> areaList = iMovieService.list(pager);
                pager.setDatas(areaList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("修改失败");
            }
        }else { //没有id 则是新增操作
            if(iMovieService.add(movieType)){
                logService.add(BaseConstants.LOG_ADD,movieType.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
                cnt = iMovieService.count();
                pager.setTotalRows(cnt);
                List<MovieTypeVo> areaList = iMovieService.list(pager);
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
