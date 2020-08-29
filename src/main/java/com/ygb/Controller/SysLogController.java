package com.ygb.Controller;

import com.ygb.entity.SysLogVo;
import com.ygb.service.ISysLogService;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class SysLogController {
    @Resource
    ISysLogService iSysLogService;

    @Resource
    ResultInfo resultInfo;

    @RequestMapping("/list")
    public ResultInfo list(SysLogVo log, PageObject pager){


        int cnt = iSysLogService.count(log);
        pager.setTotalRows(cnt);
        List<SysLogVo> logList = iSysLogService.list(log,pager);
        pager.setDatas(logList);
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        String[] logTypes={"新增","修改","删除","登陆","修改密码","延期"};
        map.put("logTypes",logTypes);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("查询成功");


        return resultInfo;
    }
}
