package com.ygb.service;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.OrderVo;
import com.ygb.entity.PlanVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderService {
    OrderVo getById(int orderId);

    List<OrderVo> list(int planId, PageObject pager);

    int count(int planId);

    boolean del(String[] arr);

    boolean add(OrderVo order);

    boolean update(OrderVo order);

    List<HallSeatVo> seatList(int hallId, int planId);

    List<HallSeatVo> seatBuyList(int hallId,int planId);

    PlanVo plan(int planId);

    void updSeat(int hallSeatId,int state);

    List<OrderVo> movieRep(PlanVo plan, PageObject pager);

    List<OrderVo> agentRep(PlanVo plan, PageObject pager);

    List<OrderVo> saleTop();

    float saleAll();

    List<OrderVo> myOrder(String mobile);

    List<AgentInfo> agentList(int agentId);

    List<OrderVo> hallRep(PlanVo plan, PageObject pager);

    List<OrderVo> bySeat(int planId,String hallSeatId);

    boolean setOrderInvalid(String orderNumber,String payType);

    List<OrderVo> orderInval(String orderNumber);

    void updateOrderInvalid(Long orderNumber);
}

