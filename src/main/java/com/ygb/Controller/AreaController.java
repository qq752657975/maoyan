package com.ygb.Controller;

import com.ygb.entity.AreaVo;
import com.ygb.service.IAreaService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/movieArea")
public class AreaController {
    @Resource
    ISysLogService logService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    IAreaService iAreaService;

    //显示所有区域信息
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager){
        int cnt = iAreaService.count();
        pager.setTotalRows(cnt);
        List<AreaVo> areaList = iAreaService.list(pager);
        pager.setDatas(areaList);
        resultInfo.setData(pager);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("查询成功");
        return resultInfo;
    }

    //删除区域
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){
        int cnt = 0;
        String arr[] = ids.split(",");
        if(iAreaService.del(arr)){
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的考区");
            //查询删除完后的信息，更新页面数据
            iAreaService.count();
            pager.setTotalRows(cnt);
            List<AreaVo> areaList = iAreaService.list(pager);
            pager.setDatas(areaList);
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setData(pager);
        }else{
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");
        }
        return resultInfo;
    }

    //修改页面回显数据
    @RequestMapping("/update")
    public ResultInfo update(int areaId){
        AreaVo area = iAreaService.getById(areaId);
        resultInfo.setState(200);
        resultInfo.setData(area);
        return resultInfo;
    }


    //修改和新增操作
    @RequestMapping("/save")
    public ResultInfo save(AreaVo area,PageObject pager){
        int cnt = 0;
       
        if(area.getAreaId()>0){ //如果传入id表示修改操作
            if(iAreaService.update(area)){
                logService.add(BaseConstants.LOG_UPDATE,area.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("修改成功");
                cnt = iAreaService.count();
                pager.setTotalRows(cnt);
                List<AreaVo> areaList = iAreaService.list(pager);
                pager.setDatas(areaList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
            }
        }else { //没有传入id 新增操作
            if(iAreaService.add(area)){
                logService.add(BaseConstants.LOG_ADD,area.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
                cnt = iAreaService.count();
                pager.setTotalRows(cnt);
                List<AreaVo> areaList = iAreaService.list(pager);
                pager.setDatas(areaList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }
}
