package com.ygb.Controller;

import com.ygb.entity.HallTypeVo;
import com.ygb.service.IHallTypeService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;

import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/hallType")
public class HallTypeController {

    @Resource
    ISysLogService logService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    IHallTypeService iHallTypeService;

    //查询所有影厅类型
    @RequestMapping("/list")
    public ResultInfo list(PageObject pager){
        int cnt = iHallTypeService.count();
        pager.setTotalRows(cnt);
        List<HallTypeVo> hallTypeList = iHallTypeService.list(pager);
        pager.setDatas(hallTypeList);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setData(pager);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }

    //新增或修改影院类别
    @RequestMapping("/save")
    public ResultInfo save(HallTypeVo hallType,PageObject pager){
        int cnt = 0;
        if(hallType.getHallTypeId()>0){ //如果传入值有id则是修改
            if(iHallTypeService.update(hallType)){
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("修改成功");
                //写系统日志
                logService.add(BaseConstants.LOG_UPDATE,hallType.toString());
                //修改之后查询数据添加页面
                cnt = iHallTypeService.count();
                pager.setTotalRows(cnt);
                List<HallTypeVo> hallTypeList = iHallTypeService.list(pager);
                pager.setDatas(hallTypeList);
                resultInfo.setData(pager);

            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("修改失败");
            }
        }else {  //如果传入值没有id，则是新增
            if(iHallTypeService.add(hallType)){
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
                //写系统日志
                logService.add(BaseConstants.LOG_ADD,hallType.toString());
                //新增之后查询参数，更新页面
                cnt = iHallTypeService.count();
                pager.setTotalRows(cnt);
                List<HallTypeVo> hallTypeList = iHallTypeService.list(pager);
                pager.setDatas(hallTypeList);
                resultInfo.setData(pager);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }


    //修改影院类型名称
    @RequestMapping("/update")
    public ResultInfo update(int hallTypeId){
        HallTypeVo hallType = iHallTypeService.getById(hallTypeId);
        resultInfo.setErrorMsg("修改成功");
        resultInfo.setFlag(true);
        resultInfo.setData(hallType);
        resultInfo.setState(200);
        return resultInfo;
    }

    //删除影院信息
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){

        String arr[] = ids.split(",");
        if(iHallTypeService.del(arr)){
            logService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的考区");
            int cnt = iHallTypeService.count();
            pager.setTotalRows(cnt);
            List<HallTypeVo> hallTypeList = iHallTypeService.list(pager);
            pager.setDatas(hallTypeList);
            resultInfo.setData(pager);
            resultInfo.setState(200);
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setState(201);
            resultInfo.setErrorMsg("删除失败");
        }
        return resultInfo;
    }




}
