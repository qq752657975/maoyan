package com.ygb.Controller;

import com.ygb.entity.HallSeatVo;
import com.ygb.entity.OrderVo;
import com.ygb.entity.PlanVo;
import com.ygb.entity.UserVo;
import com.ygb.service.IOrderService;
import com.ygb.service.ISysLogService;
import com.ygb.service.IUserService;
import com.ygb.util.BaseConstants;
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
@RequestMapping("/order")
public class OrderController {

    @Resource
    IUserService iUserService;
    @Resource
    IOrderService iOrderService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    ISysLogService iSysLogService;
    //订单记录条件存储
    @RequestMapping("/list")
    public ResultInfo list(int planId,PageObject pager,HttpSession session){
        //判断前台是否传入id

            //如果有id 则存起来 方便下次使用
            session.setAttribute("planId",planId);

        Map params = getParams(planId, pager);
        resultInfo.setData(params);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }
    //查询订单列表
    private Map getParams(int planId , PageObject pager){
        //根据排片id查询订单的总数
        int cnt = iOrderService.count(planId);
        pager.setTotalRows(cnt);
        //查询所有的订单列表
        List<OrderVo> movieList = iOrderService.list(planId,pager);
        //通过id 查询座位数
        PlanVo plan = iOrderService.plan(planId);
        //取出所有没有被购买的座位列表
        List<HallSeatVo> seatList = iOrderService.seatList(plan.getHallId(),plan.getPlanId());
        pager.setDatas(movieList);
        //把查询出来的参数返回到前台
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("order",new OrderVo());
        map.put("seatList",seatList);
        map.put("plan",plan);
        return map;
    }

    //删除
    @RequestMapping("/del")
    public ResultInfo del(String ids,HttpSession session,PageObject pageObject){
        int planId  = (Integer) session.getAttribute("planId");
        String [] arr = ids.split(",");
        if(iOrderService.del(arr)){
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            Map params = getParams(planId,pageObject);
            resultInfo.setData(params);
        }else{
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");
        }
        return resultInfo;
    }

    //修改或新增
    @PostMapping("/save")
    public ResultInfo save(HttpSession session,OrderVo order){
        int planId  = (Integer) session.getAttribute("planId");
        UserVo u=null;
        Map<String,Object> map;
        //判断手机号判断是否有该用户
        if(order.getMobile()!=null) {
            u = iUserService.getById(order.getMobile());
        }
        //根据上面查询的结果，判断用户是否登录
        if(u==null){
            resultInfo.setErrorMsg("该手机号码不是注册用户，购票失败。");
            return resultInfo;
        }
        order.setPlanId(planId);
        if(order.getOrderId()>0){ //如果有订单信息则是修改
            if(iOrderService.update(order)){

                iSysLogService.add(BaseConstants.LOG_UPDATE,order.toString());
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
                resultInfo.setState(200);
                //查询修改后的数据，更新页面信息
                map = getParams(planId, new PageObject());
                resultInfo.setData(map);
            }else{
                resultInfo.setErrorMsg("修改失败");
                resultInfo.setFlag(false);
                resultInfo.setState(201);
            }
        }else { //如果没有订单信息则是新增
            if(iOrderService.add(order)){
                //写入系统日志
                iSysLogService.add(BaseConstants.LOG_ADD,order.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("添加成功");
                resultInfo.setFlag(true);
                //查询新增后的数据，更新页面信息
                map = getParams(planId, new PageObject());
                resultInfo.setData(map);
            }else{
                resultInfo.setState(201);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }


}
