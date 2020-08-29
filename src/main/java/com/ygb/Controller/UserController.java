package com.ygb.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ygb.entity.UserVo;
import com.ygb.service.ISysLogService;
import com.ygb.service.IUserService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    ResultInfo resultInfo;

    @Resource
    ISysLogService iSysLogService;

    @Resource
    IUserService iUserService;

    private String imgPath = "D:/img";
    @RequestMapping("/list")
    //后台显示用户列表
    public ResultInfo list(PageObject pager, UserVo user, HttpSession session){
        //记录查找用户的条件
        session.setAttribute("user",user);
        //查询用户的总数量
        int cnt = iUserService.count(user);
        pager.setTotalRows(cnt);
        //条件查询用户列表
        List<UserVo> userList = iUserService.list(user,pager);
        pager.setDatas(userList);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setData(pager);
        resultInfo.setState(200);
        return resultInfo;
    }

    //删除用户
    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){

        System.out.println("ids="+ids);
        UserVo u = new UserVo();
        String arr[] = ids.split(",");
        if(iUserService.del(arr)){
            iSysLogService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的考区");
            resultInfo.setErrorMsg("删除成功");
            resultInfo.setFlag(true);
            resultInfo.setState(200);
            //重写查找用户数量
            int cnt = iUserService.count(u);
            pager.setTotalRows(cnt);
            //按照条件查询用户列表 更新前台用户数据
            List<UserVo> userList = iUserService.list(u,pager);
            pager.setDatas(userList);
            resultInfo.setData(pager);
        }else{
            resultInfo.setState(201);
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");
        }
        return resultInfo;
    }

    //修改用户页面回显数据
    @RequestMapping("/update")
    public ResultInfo update(String mobile){
        UserVo user = iUserService.getById(mobile);
        resultInfo.setData(user);
        return resultInfo;
    }

    //新增或修改用户
    @RequestMapping("/save")
    public ResultInfo save(UserVo user, MultipartFile file1,PageObject pager,HttpSession session) throws  Exception{
        UserVo u = iUserService.getById(user.getMobile());
        if(user.getId() > 0){ //如果传入用户数据，则表示修改操作
            //判断用户是否传入头像
            if(file1 != null) {
                if (file1.getOriginalFilename().equals("")) {
                    user.setHeadImg(u.getHeadImg());
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
                    user.setHeadImg("http://localhost:8080/img/" + newFileName);
                }
            }
            if(iUserService.update(user)){
                iSysLogService.add(BaseConstants.LOG_UPDATE,user.toString());
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setState(200);
                resultInfo.setFlag(true);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
                return resultInfo;
            }
        }else {
            if(u != null){
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("该手机号已注册");
                return resultInfo;
            }
            if(file1 != null) {
                if (file1.getOriginalFilename().equals("")) {
                    user.setHeadImg("card/headImg.png");
                } else {
                    String picName = UUID.randomUUID().toString();
                    // 截取文件的扩展名(如.jpg)
                    String oriName = file1.getOriginalFilename();
                    System.out.println("--上传文件名-->>" + oriName);
                    String extName = oriName.substring(oriName.lastIndexOf("."));

                    String newFileName = picName + extName;
                    File targetFile = new File(imgPath,newFileName);
                    // 保存文件
                    file1.transferTo(targetFile);
                    user.setHeadImg("http://localhost:8080/img/" + newFileName);
                }
            }
                if (iUserService.add(user)) {
                    iSysLogService.add(BaseConstants.LOG_ADD, user.toString());
                    resultInfo.setErrorMsg("添加成功");
                    resultInfo.setState(200);
                    resultInfo.setFlag(true);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setState(201);
                    resultInfo.setErrorMsg("添加失败");
                    return resultInfo;
                }

        }
        //查询成功修改或成功添加的数据，更新页面显示
        UserVo userVo = (UserVo) session.getAttribute("user");
        int cnt = iUserService.count(userVo );
        pager.setTotalRows(cnt);
        List<UserVo> userList = iUserService.list(userVo,pager);
        pager.setDatas(userList);
        resultInfo.setData(pager);
        return resultInfo;
    }

}
