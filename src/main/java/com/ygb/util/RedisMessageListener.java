package com.ygb.util;

import com.ygb.entity.OrderVo;
import com.ygb.service.IOrderService;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

//redis 监听消息通知
@Component
public class RedisMessageListener implements MessageListener {

    @Resource
    IOrderService iOrderService;




    /*
       处理消息
        message：完整消息（频道的信息，以及具体的消息内容）
        pattern：获取的频道信息
     */

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("--------------------------------------能进来吗");
        String key = new String(message.getBody());
        if(key.startsWith("orderNumber")){
            String orderNumber = key.split(":")[1];
            List<OrderVo> orderVos = iOrderService.orderInval(orderNumber);
            for(int i = 0;i<orderVos.size();i++){
                iOrderService.updateOrderInvalid(orderVos.get(i).getOrderNumber());
            }

        }
    }
}
