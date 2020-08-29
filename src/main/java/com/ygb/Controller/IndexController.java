package com.ygb.Controller;

import com.ygb.entity.*;
import com.ygb.service.*;
import com.ygb.util.BaseConstants;
import com.ygb.util.ResultInfo;
import com.ygb.util.codeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

//前端控制器
@RestController
@RequestMapping("/index")
public class IndexController {

    private String imgPath = "D:/img";
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    RedisTemplate<String,Object>  stringRedisTemplate;
    @Resource
    IUserService iUserService;

    @Resource
    IAgentInfoService iAgentInfoService;

    @Resource
    IOrderService iOrderService;

    @Resource
    ResultInfo resultInfo;
    @Resource
    IMovieHallService iMovieHallService;
    @Resource
    IPlanService iPlanService;
    @Resource
    ISysLogService iSysLogService;

    //前端选座
    @RequestMapping("/seat")
    public ResultInfo seat(int planId){
        //查询影片的相关信息
        PlanVo plan =iAgentInfoService.getPlanById(planId);
        //查询该影厅的座位
        List<HallSeatVo> seatList = iOrderService.seatBuyList(plan.getHallId(),plan.getPlanId());
        MovieHallVo hall = iMovieHallService.getById(plan.getHallId());
        Map<String,Object> map = new HashMap<>();
        map.put("plan",plan);
        map.put("seatList",seatList);
        map.put("hall",hall);
        resultInfo.setFlag(true);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("查询成功");
        return resultInfo;
    }
    //生成订单锁定座位
    @RequestMapping("/addOrder")
    public ResultInfo addOrder(HttpSession session, int planId, String seatIds,String mobile,String phone) {
        //查询此座位是否已被购买
        String substring = seatIds.substring(0, seatIds.length() - 1);
        List<OrderVo> orderVos = iOrderService.bySeat(planId, substring);
        float seatPrice = 0;

        if (orderVos.size() <= 0) {
            UserVo user = (UserVo) session.getAttribute("member");
            if(user != null || mobile != null) {
                PlanVo plan = iPlanService.getById(planId);
                seatPrice = plan.getSeatPrice();
                String arr[] = seatIds.split(",");
                System.out.println(arr);
                if (arr != null && arr.length > 0) {
                    long orderNumber = new Date().getTime() / 1000 - 10 * 10;
                    for (int i = 0; i < arr.length; i++) {
                        OrderVo order = new OrderVo();
                        order.setPlanId(planId);
                        order.setHallSeatId(Integer.parseInt(arr[i]));
                        order.setSalePrice(plan.getSeatPrice());
                        order.setInval(2);
                        if(user != null){
                            order.setMobile(user.getMobile());
                        }else {
                            order.setMobile(mobile);
                        }
                        //生成订单编号
                        order.setOrderNumber(orderNumber);
//                    order.setMobile(user.getMobile());
                        order.setState("购票");
                        iOrderService.add(order);
                        iSysLogService.add(1, order.toString());
                        //想redis存入订单号
                        String key = "orderNumber:" + orderNumber;
                        String orderNuber = orderNumber +"";
                        redisTemplate.opsForValue().set(key,orderNuber,15,TimeUnit.MINUTES);
                    }
                    Map<String, Object> map = new HashMap<>();
                    long nowTime = new Date().getTime();//现在时间（时间戳）
                    long endTime = new Date(nowTime + 15 * 60 * 1000).getTime();//结束时间（时间戳）
                    seatPrice = arr.length * seatPrice;
                    map.put("orderNuber", orderNumber);
                    map.put("time", endTime);
                    map.put("seatPrice", seatPrice);
                    resultInfo.setData(map);
                    resultInfo.setErrorMsg("锁定座位成功");
                    resultInfo.setFlag(true);
                    resultInfo.setState(200);
                    return resultInfo;
                }
            }
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您没有登录,请先登录");
            resultInfo.setState(205);
            return resultInfo;
        }

        resultInfo.setState(202);
        resultInfo.setErrorMsg("此位置已被购买");
        resultInfo.setFlag(true);
        return resultInfo;
    }
    //支付订单
    @RequestMapping("setOrder")
    public ResultInfo setOrder(String orderNumber,String payType){


        if(iOrderService.setOrderInvalid(orderNumber,payType)){
            //随机生成取票码
            Random random = new Random();
            String result="";
            for(int i=0;i<8;i++){
                //首字母不能为0
                result += (random.nextInt(9)+1);
            }
            //拼接key
            String key = "orderNumber:" + orderNumber;
            //支付成功时清除key
            redisTemplate.delete(orderNumber);
            resultInfo.setData(result);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("支付成功");
            resultInfo.setState(200);
            return resultInfo;
        }
       resultInfo.setState(201);
        resultInfo.setFlag(false);
        resultInfo.setErrorMsg("支付失败");
        return resultInfo;
    }

    //用户获取验证码
    @RequestMapping("/code")
    public ResultInfo code(String  mobile){
        //获取验证码
        String code = codeUtil.getCode(6);
        //拼接用户验证码的key
        String codeKey = "Verify_code:" + mobile + ":code";
        //拼接用户发送验证次数的key
        String codeCount = "Verify_code:" + mobile + ":codeCount1";
        //获取用户发送验证码的次数
        String count = (String) stringRedisTemplate.opsForValue().get(codeCount);
        if(null == count){
            stringRedisTemplate.opsForValue().set(codeCount,"1",1, TimeUnit.DAYS);
        }else if(Integer.parseInt(count) <= 2 ){
            stringRedisTemplate.boundValueOps(codeCount).increment(1);
        }else if(Integer.parseInt(count) > 2){
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("一天只能发送3次验证码");
            return resultInfo;
        }
        stringRedisTemplate.opsForValue().set(codeKey,code,120, TimeUnit.SECONDS);
        String s = (String) stringRedisTemplate.opsForValue().get(codeKey);
        resultInfo.setErrorMsg("发送成功");
        resultInfo.setData(s);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }

    //用户使用验证码登录
    @RequestMapping("/sLogin")
    public ResultInfo sLogin(String mobile,String code){
        //拼接用户的key
        String codeKey = "Verify_code:" + mobile + ":code";
        //查询手机验证码是否正确
        String s = (String) stringRedisTemplate.opsForValue().get(codeKey);

        System.out.println("code----------------" + s);
        if(s == null ||!s.equals(code)){
            resultInfo.setErrorMsg("验证码错误");
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        //查询用户是否注册过
        UserVo u = iUserService.getById(mobile);
        if(u == null){
            //给用户注册
            u = new UserVo();
            u.setSex("男");
            u.setHeadImg("card/headImg.png");
            u.setState(1);
            u.setMobile(mobile);

            iUserService.add(u);
            iSysLogService.add(BaseConstants.LOG_ADD,u.toString());
            //在重写查询注册后的用户信息
            u = iUserService.getById(mobile);
        }
        //删除验证码
        stringRedisTemplate.delete(codeKey);
        resultInfo.setData(u);
        resultInfo.setErrorMsg("登录成功");
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }

    //用户使用密码登录
    @RequestMapping("/pLogin")
    public ResultInfo pLogin(String mobile,String password,String code,HttpSession session){
        //拼接验证码的key
        String code1 = "Code:" + code.toUpperCase() + ":code";
        //从rides中获取验证码
        Boolean flag = stringRedisTemplate.hasKey(code1);

        String msg = "";

        if(!flag || code == null) {
            msg = "验证码错误";
            resultInfo.setErrorMsg(msg);
            resultInfo.setState(102);
            resultInfo.setFlag(false);
            return resultInfo;
        }


        UserVo u = iUserService.getById(mobile);
        if(u==null){
            resultInfo.setErrorMsg("用户不存在");
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        if(!u.getPassword().equals(password)){
            resultInfo.setErrorMsg("密码错误");
            resultInfo.setState(202);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        if(u.getState()==0){
            resultInfo.setErrorMsg("您的账号禁止登录");
            resultInfo.setState(203);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        //清空验证码
        stringRedisTemplate.delete(code1);
        session.setAttribute("member",u);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setErrorMsg("登录成功");
        resultInfo.setData(u);

        return resultInfo;
    }

    //客服端用户上传头像
    @RequestMapping("/uploadHead")
    public ResultInfo uploadHead(UserVo user , MultipartFile file1, HttpSession session) throws  Exception {
        UserVo u = iUserService.getById(user.getMobile());
        if (file1 != null && u != null) {
            String picName = UUID.randomUUID().toString();
            // 截取文件的扩展名(如.jpg)
            String oriName = file1.getOriginalFilename();
            System.out.println("--上传文件名-->>" + oriName);
            String extName = oriName.substring(oriName.lastIndexOf("."));
            String newFileName = picName + extName;
            File targetFile = new File(imgPath, newFileName);
            // 保存文件
            file1.transferTo(targetFile);
            u.setHeadImg("http://localhost:8080/img/" + newFileName);
            iUserService.update(u);
            iSysLogService.add(BaseConstants.LOG_UPDATE, u.toString());
        }
        session.setAttribute("member", u);
        resultInfo.setData(u);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("修改成功");
        return resultInfo;
    }

    //小程序和客服端修改个人信息
    @RequestMapping("/userReg")
    public ResultInfo userReg(UserVo user ,HttpSession session){
        UserVo u = iUserService.getById(user.getMobile());
        if(u !=null){

            user.setState(u.getState());
            iUserService.update(user);
            iSysLogService.add(BaseConstants.LOG_UPDATE,user.toString());
            u = iUserService.getById(user.getMobile());
            session.setAttribute("member",u);
            resultInfo.setErrorMsg("修改成功");
            resultInfo.setFlag(true);
            resultInfo.setData(u);
            resultInfo.setState(200);
            return resultInfo;
        }
        resultInfo.setErrorMsg("用户不能为空");
        resultInfo.setFlag(false);
        resultInfo.setState(201);
        return resultInfo;

    }
    //我的订单
    @RequestMapping("/myOrder")
    public ResultInfo myOrder(String mobile,HttpSession session){
        if(session.getAttribute("member")!=null){
            UserVo user = (UserVo)session.getAttribute("member");
            List<OrderVo> myOrderList =iOrderService.myOrder(user.getMobile());
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("查询成功");
            resultInfo.setData(myOrderList);
            return resultInfo;
        }
        if(mobile != null){
            List<OrderVo> myOrderList =iOrderService.myOrder(mobile);
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("查询成功");
            resultInfo.setData(myOrderList);
            return resultInfo;
        }
            resultInfo.setErrorMsg("您没有登录");
            resultInfo.setFlag(false);
            resultInfo.setState(201);
            return resultInfo;


    }

}
