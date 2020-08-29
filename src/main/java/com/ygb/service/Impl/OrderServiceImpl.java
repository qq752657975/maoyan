package com.ygb.service.Impl;

import com.ygb.Dao.IOrderDao;
import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.OrderVo;
import com.ygb.entity.PlanVo;
import com.ygb.service.IOrderService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    IOrderDao iOrderDao;

    @Override
    public OrderVo getById(int orderId) {
        return iOrderDao.getById(orderId);
    }

    @Override
    public List<OrderVo> list(int planId, PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return iOrderDao.list(planId,pager);
    }

    @Override
    public int count(int planId) {

        return iOrderDao.count(planId);
    }

    @Override
    public boolean del(String[] arr) {
        if(arr != null && arr.length >0){
            int del = iOrderDao.del(arr);
            if(del > 0){
                return  true;
            }
        }

        return false;
    }


    @Transactional
    public synchronized boolean add(OrderVo order) {
        if(order != null){
            int add = iOrderDao.add(order);
            if(add > 0){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean update(OrderVo order) {
        if(order != null){
            int update = iOrderDao.update(order);

            if(update > 0){
                return true;
            }
        }

        return false;
    }
    //后台下拉框,能购票座位列表
    @Override
    public List<HallSeatVo> seatList(int hallId, int planId) {

        return iOrderDao.seatList(hallId,planId);
    }
    //前台购票座位列表
    @Override
    public List<HallSeatVo> seatBuyList(int hallId, int planId) {


        return iOrderDao.seatBuyList(hallId,planId);
    }

    @Override
    public PlanVo plan(int planId) {

        return iOrderDao.plan(planId);
    }

    @Override
    public void updSeat(int hallSeatId, int state) {
            iOrderDao.updSeat(hallSeatId,state);
    }
    //票房统计
    @Override
    public List<OrderVo> movieRep(PlanVo plan, PageObject pager) {
        pager.setPageRow(9);
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        int cnt = iOrderDao.movieCountRep(plan);
        pager.setTotalRows(cnt);
        return iOrderDao.movieRep(plan,pager);

    }
    //影院统计
    @Override
    public List<OrderVo> agentRep(PlanVo plan, PageObject pager) {
        pager.setPageRow(9);
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        int cnt = iOrderDao.agentCountRep(plan);
        pager.setTotalRows(cnt);
        return iOrderDao.agentRep(plan,pager);
    }
    //今日票房汇总
    @Override
    public List<OrderVo> saleTop() {

        return iOrderDao.saleTop();
    }
    //今日票房
    @Override
    public float saleAll() {
        return iOrderDao.saleAll();
    }
    //我的订单
    @Override
    public List<OrderVo> myOrder(String mobile) {

        return iOrderDao.myOrder(mobile);
    }

    @Override
    public List<AgentInfo> agentList(int agentId) {

        return iOrderDao.agentList(agentId);
    }

    //影厅统计
    public List<OrderVo> hallRep(PlanVo plan, PageObject pager){
        pager.setPageRow(9);
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        int cnt = iOrderDao.hallCountRep(plan);
        pager.setTotalRows(cnt);
        return iOrderDao.hallRep(plan,pager);
    }

    @Override
    public List<OrderVo> bySeat(int planId, String hallSeatId) {
        return iOrderDao.bySeat(planId,hallSeatId);
    }

    @Override
    public boolean setOrderInvalid(String orderNumber,String payType) {
        int i = iOrderDao.setOrderInvalid(orderNumber,payType);
        if(i > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<OrderVo> orderInval(String orderNumber) {
        return iOrderDao.orderInval(orderNumber);
    }

    @Override
    public void updateOrderInvalid(Long orderNumber) {
        iOrderDao.updateOrderInvalid(orderNumber);
    }
}
