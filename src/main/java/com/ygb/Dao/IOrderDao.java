package com.ygb.Dao;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.OrderVo;
import com.ygb.entity.PlanVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDao {

    OrderVo getById(int orderId);
    List<OrderVo> list(@Param("planId") int planId, @Param("pager") PageObject pager);
    int count(@Param("planId") int planId);
    int del(String[] ids);
    int add(OrderVo order);
    int update(OrderVo order);
    List<HallSeatVo> seatList(@Param("hallId") int hallId, @Param("planId") int planId);
    List<HallSeatVo> seatBuyList(@Param("hallId") int hallId,@Param("planId") int planId);

    PlanVo plan(int planId);
    void updSeat(@Param("hallSeatId")int hallSeatId,@Param("state") int state);
    //票房统计
    List<OrderVo> movieRep(@Param("plan") PlanVo plan, @Param("pager") PageObject pager);
    int movieCountRep(@Param("plan") PlanVo plan);
    //影院统计
    List<OrderVo> agentRep(@Param("plan") PlanVo plan, @Param("pager") PageObject pager);
    int agentCountRep(@Param("plan") PlanVo plan);
    List<AgentInfo> agentList(@Param("agentId") int agentId);
    //影厅统计
    List<OrderVo> hallRep(@Param("plan") PlanVo plan, @Param("pager") PageObject pager);
    int hallCountRep(@Param("plan") PlanVo plan);
    //今日票房
    List<OrderVo> saleTop();
    //今日票房汇总
    float saleAll();
    //我的订单
    List<OrderVo> myOrder(@Param("mobile") String mobile);
    //查询座位是否已被购买
    List<OrderVo> bySeat(@Param("planId") int planId,@Param("hallSeatId") String hallSeatId);
    //支付 完成 修改订单状态
    int setOrderInvalid(@Param("orderNumber") String orderNumber,@Param("payType") String payType);

    List<OrderVo> orderInval(String orderNumber);

    void updateOrderInvalid(Long orderNumber);
}
