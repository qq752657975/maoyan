package com.ygb.Controller;


import com.ygb.entity.AgentInfo;
import com.ygb.entity.Manager;
import com.ygb.entity.ProvinceVo;
import com.ygb.entity.UserVo;
import com.ygb.service.IManagerService;
import com.ygb.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
//管理员登录
@RestController
public class LoginController {

    @Resource
    private IManagerService iManagerServer;

    @Resource
    private ResultInfo resultInfo;

    private String imgPath = "D:/img";
    //登陆
    @RequestMapping("/login")
    public ResultInfo login(Manager manager, String randCode,HttpSession session){
        System.out.println("------------" + manager.getManagerPassword() + "-------------" + manager.getManagerAccount());
        if(session.getAttribute("CHECKCODE_SERVER") == null){
                resultInfo.setState(101);
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("验证码失效");
                return resultInfo;
        }
        String msg = "";
        String rand = session.getAttribute("CHECKCODE_SERVER").toString();
        if(rand == null || randCode == null){
            msg = "验证码不能为空";
            resultInfo.setErrorMsg(msg);
            resultInfo.setState(102);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        rand = rand.toUpperCase();
        randCode = randCode.toUpperCase();
        if(!rand.equals(randCode)){
            msg = "验证码错误";
            resultInfo.setErrorMsg(msg);
            resultInfo.setState(103);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        manager.setManagerPassword(AesUtils.encryptStr(manager.getManagerPassword(),AesUtils.SECRETKEY));

        Manager mgr = iManagerServer.login(manager);
        System.out.println(mgr.getImg());

        if(mgr == null){
            msg = "用户名或密码错误,或者账户被禁用";
            resultInfo.setErrorMsg(msg);
            resultInfo.setState(104);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        if(mgr.getAgent().getInvalid() == 0){
            msg = "该影院用户禁止登陆";
            resultInfo.setErrorMsg(msg);
            resultInfo.setState(105);
            resultInfo.setFlag(false);
            return resultInfo;
        }
        //清空验证码，为了保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        session.setAttribute("userinfo",mgr);
        System.out.println("login.........");
        resultInfo.setState(200);
        msg = "登陆成功";
        resultInfo.setErrorMsg(msg);
        resultInfo.setFlag(true);
        return resultInfo;
    }
    //查询个人信息
    @RequestMapping("/personal")
    public ResultInfo personal(){
        Manager userInfo = ContextUtils.getUserInfo();
        if(userInfo != null){
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            resultInfo.setData(userInfo);
        }else{
            resultInfo.setState(201);
            resultInfo.setFlag(false);
        }
        return resultInfo;
    }

    //修改页面回显信息
    @RequestMapping("/echo")
    public ResultInfo updateEcho(Manager manager){
        List<JsonData> mtList = new ArrayList<>();
        //如果登录的角色是系统管理员，则也可以新增影院管理员
        if (ContextUtils.getUserInfo().getManagerType() == 3){
            JsonData e1 = new JsonData("0","影院管理员");
            JsonData e2 = new JsonData("3","系统管理员");
            mtList.add(e1);
            mtList.add(e2);
        }else if(ContextUtils.getUserInfo().getManagerType() == 0){
            JsonData e1 = new JsonData("0","影院管理员");
            mtList.add(e1);
        }
        int agentId = 0;
        //如果不是系统管理员，新增管理员只能是自己影院的
        if(ContextUtils.getUserInfo().getManagerType() != 3){
            agentId = ContextUtils.getUserInfo().getAgentId();
        }
        //根据影院id 查询该影院的所有信息
        List<AgentInfo> agentInfoList =iManagerServer.agentList(agentId);
        Map<String,Object> map = new HashMap<>();
        map.put("mtList",mtList);
        map.put("agentInfoList",agentInfoList);
        Manager byId = iManagerServer.getById(manager.getManagerId());
        map.put("manager",byId);
        resultInfo.setFlag(true);
        resultInfo.setData(map);
        System.out.println(resultInfo.getData());
        return resultInfo;
    }
    //管理员列表
    @RequestMapping("/list")
    public ResultInfo list(AgentInfo agentInfo, PageObject pageObject,Manager manager,HttpSession session){
        if(pageObject.getStartRow() <= 1){
            pageObject.setStartRow(1);
        }

        List<ProvinceVo> provinceVoList = iManagerServer.provinceList();

        //0、影院管理员 3、系统管理员
        if(ContextUtils.getUserInfo().getManagerType() != 3){

            agentInfo.setAgentId(ContextUtils.getUserInfo().getAgentId());
        }
        //获取查询后的总条数
        int cnt = iManagerServer.count(agentInfo);
        System.out.println("--------------------" + agentInfo);
        System.out.println("------------------" + cnt);
        //设置总条数
        pageObject.setTotalRows(cnt);
        //获取查询后的数据
        List<Manager> managers = iManagerServer.mgrList(agentInfo,pageObject,manager);
        //放入分页
        pageObject.setDatas(managers);

        Map<String,Object> map = new HashMap<>();

        //查询后的列表数据、省份、条件放入一个map中
        map.put("pager",pageObject);

        map.put("provList",provinceVoList);
        //主要传入前台有一个回显的操作
        map.put("agent",agentInfo);
        resultInfo.setState(200);
        resultInfo.setData(map);

        return resultInfo;
    }
    //修改个人信息
    @RequestMapping("/updateInfo")
    public ResultInfo updateInfo(Manager manager, HttpSession session, MultipartFile file1) throws IOException {
        Manager byId = iManagerServer.getById(manager.getManagerId());
        if(file1 != null){
            if (file1.getOriginalFilename().equals("")) {
                manager.setImg(byId.getImg());
            } else {
                String picName = UUID.randomUUID().toString();
                // 截取文件的扩展名(如.jpg)
                String oriName = file1.getOriginalFilename();
                System.out.println("--上传文件名-->>" + oriName);
                String extName = oriName.substring(oriName.lastIndexOf("."));
                //平均生成的图片随机名和扩展名
                String newFileName = picName + extName;
                File targetFile = new File(imgPath, newFileName);
                // 保存文件
                file1.transferTo(targetFile);
                manager.setImg("http://localhost:8080/img/" + newFileName);
            }
        }

        int flag = iManagerServer.updateInfo(manager);

        //查询修改后的信息
        manager = iManagerServer.getById(manager.getManagerId());
        session.setAttribute("userInfo",manager);
        resultInfo.setData(manager);
        resultInfo.setErrorMsg("修改成功");
        resultInfo.setState(200);
        return resultInfo;
    }

    //修改个人密码
    @RequestMapping("updatePwd")
    public ResultInfo updatePwd(Manager manager) {
        //转换密码
        String pwd = AesUtils.encryptStr(manager.getManagerPassword(), AesUtils.SECRETKEY);
        //查询该账户的密码
        Manager mgr = iManagerServer.getById(manager.getManagerId());
        String msg = "";
        //使用转换后的密码与数据库的密码进行比对
        if (mgr.getManagerPassword().equals(pwd)) {
            //转换密码，进行保存
            manager.setManagerPassword(AesUtils.encryptStr(manager.getManagerPassword(), AesUtils.SECRETKEY));
            int flag = iManagerServer.updatePwd(manager);
            if (flag > 0) {
                msg = "修改成功";
                resultInfo.setErrorMsg(msg);
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                return resultInfo;
            } else {
                msg = "修改失败";
                resultInfo.setErrorMsg(msg);
                resultInfo.setState(300);
                resultInfo.setFlag(false);
                return resultInfo;
            }
        }
        msg = "密码不对,请重新输入";
        resultInfo.setErrorMsg(msg);
        resultInfo.setState(201);
        resultInfo.setFlag(false);
        return resultInfo;
    }

    //查询权限，并根据权限回显新增的选项
    @RequestMapping("/init")
    public ResultInfo init(){
        List<JsonData> mtList = new ArrayList<>();
        //如果登录的角色是系统管理员，则也可以新增影院管理员
        if (ContextUtils.getUserInfo().getManagerType() == 3){
            JsonData e1 = new JsonData("0","影院管理员");
            JsonData e2 = new JsonData("2","系统管理员");
            mtList.add(e1);
            mtList.add(e2);
        }else if(ContextUtils.getUserInfo().getManagerType() == 0){
            JsonData e1 = new JsonData("0","影院管理员");
            mtList.add(e1);
        }

        int agentId = 0;
        //如果不是系统管理员，新增管理员只能是自己影院的
        if(ContextUtils.getUserInfo().getManagerType() != 3){
            agentId = ContextUtils.getUserInfo().getAgentId();
        }
        //根据影院id 查询该影院的所有信息
        List<AgentInfo> agentInfoList =iManagerServer.agentList(agentId);
        Map<String,Object> map = new HashMap<>();
        map.put("mtList",mtList);
        map.put("agentInfoList",agentInfoList);
        resultInfo.setState(200);
        resultInfo.setData(map);
        return  resultInfo;
    }

    //修改或新增
    @RequestMapping("/save")
    public ResultInfo save(Manager manager){
        Manager mgr = null;
        //如果传入的值有id 说明是修改操作
        if(manager.getManagerId() > 0 ){
            //判断密码是否为空,如果为空，把以前的密码设置回去

            if(iManagerServer.update(manager)){
                resultInfo.setState(200);
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
                return resultInfo;
            }
        }else{  //做新增功能
            //将传入的密码加密

            if(!iManagerServer.getManagerAccount(manager.getManagerAccount())){
                resultInfo.setErrorMsg("该用户已注册");
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                return resultInfo;
            }
            manager.setManagerPassword(AesUtils.encryptStr(manager.getManagerPassword(),AesUtils.SECRETKEY));
            //然后在保存到数据库
            if(iManagerServer.add(manager)){
                resultInfo.setState(200);
                resultInfo.setErrorMsg("新增成功");
                resultInfo.setFlag(true);
            }else{
                resultInfo.setState(201);
                resultInfo.setErrorMsg("新增失败");
                resultInfo.setFlag(false);
                return resultInfo;
            }
        }
        //修改还是新增 更新页面显示数据
        AgentInfo agent = new AgentInfo();
        List<ProvinceVo> provList = iManagerServer.provinceList();
        int cnt = iManagerServer.count(agent);
        PageObject pager = new PageObject();
        pager.setTotalRows(cnt);
        List<Manager> mgrList = iManagerServer.mgrList(agent,pager,new Manager());
        pager.setDatas(mgrList);
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("provList",provList);
        map.put("agent",agent);
        resultInfo.setData(map);
        return resultInfo;
    }

    //删除管理员
    @RequestMapping("/del")
    public ResultInfo del(String ids,PageObject pageObject,HttpSession session,Manager manager,AgentInfo agentInfo){

        if(iManagerServer.del(ids)){
            //是否有查询条件
            agentInfo = (AgentInfo) session.getAttribute("agent");
            //如果没有查询条件则赋一个新对象
            if(agentInfo == null){
                agentInfo = new AgentInfo();
            }
            //更新数据，重写显示页面
            List<ProvinceVo> provList = iManagerServer.provinceList();
            int cnt = iManagerServer.count(agentInfo);

            pageObject.setTotalRows(cnt);
            List<Manager> mgrList = iManagerServer.mgrList(agentInfo,pageObject,manager);
            pageObject.setDatas(mgrList);
            Map<String,Object> map = new HashMap<>();

            map.put("pager",pageObject);
            map.put("provList",provList);
            map.put("agent",agentInfo);

            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setState(200);
            resultInfo.setData(map);

        }else{

            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");

        }
        return resultInfo;
    }
    //退出
    @RequestMapping("/eint")
    public void enit(HttpSession session){
        session.setAttribute("userinfo",null);
    }
}
