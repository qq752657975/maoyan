package com.ygb.Controller;

import com.ygb.entity.PlayTimeVo;
import com.ygb.service.IPlayTimeService;
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
@RequestMapping("/playTime")
public class PlayTimeController {
    @Resource
    ISysLogService logService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    IPlayTimeService playTimeService;
    //查询所有的播放时段
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager, HttpSession session){
        int cnt = playTimeService.count();
        pager.setTotalRows(cnt);
        List<PlayTimeVo> playTimeList = playTimeService.list(pager);
        pager.setDatas(playTimeList);
        resultInfo.setData(pager);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }
    //删除影片播放时段
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){

        String arr[] = ids.split(",");
        if(playTimeService.del(arr)){
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的播放时段");
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            resultInfo.setErrorMsg("删除成功");
            //查询删除之后的数据，更新页面数据
            int cnt = playTimeService.count();
            pager.setTotalRows(cnt);
            List<PlayTimeVo> playTimeList = playTimeService.list(pager);
            pager.setDatas(playTimeList);
            resultInfo.setData(pager);
            return resultInfo;
        }
        resultInfo.setFlag(false);
        resultInfo.setState(201);
        resultInfo.setErrorMsg("删除失败");
        return resultInfo;
    }
    //回显要修改以前的信息
    @RequestMapping("/update")
    public ResultInfo update(int playTimeId){
        PlayTimeVo playTime = playTimeService.getById(playTimeId);
        resultInfo.setData(playTime);
        return resultInfo;
    }
    //新增或者修改播放时段信息
    @RequestMapping("/save")
    public ResultInfo save(PlayTimeVo playTime,PageObject pager){
       int cnt = 0;
        if(playTime.getPlayTimeId()>0){ //传入id 表示修改
            if(playTimeService.update(playTime)){
                logService.add(BaseConstants.LOG_UPDATE,playTime.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
                cnt = playTimeService.count();
                pager.setTotalRows(cnt);
                List<PlayTimeVo> playTimeList = playTimeService.list(pager);
                pager.setDatas(playTimeList);
                resultInfo.setData(pager);
            }else {
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
            }
        }else {
            if(playTimeService.add(playTime)){
                logService.add(BaseConstants.LOG_ADD,playTime.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("新增成功");
                resultInfo.setFlag(true);
                cnt = playTimeService.count();
                pager.setTotalRows(cnt);
                List<PlayTimeVo> playTimeList = playTimeService.list(pager);
                pager.setDatas(playTimeList);
                resultInfo.setData(pager);
                return resultInfo;
            }else {
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }


}
