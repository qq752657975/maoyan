package com.ygb.Controller;

import com.ygb.entity.ActorVo;
import com.ygb.service.IActorService;
import com.ygb.service.IMovieActorService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ygb.util.BaseConstants.absPath;

@RequestMapping("/actor")
@RestController
public class ActorController {
    @Resource
    ResultInfo resultInfo;
    @Resource
    IActorService iActorService;
    @Resource
    ISysLogService iSysLogService;
    @Resource
    IMovieActorService iMovieActorService;

    @RequestMapping("/list")
    public ResultInfo list(PageObject pager, HttpSession session){
        int cnt = iActorService.count();
        pager.setTotalRows(cnt);
        List<ActorVo> actorList = iActorService.list(pager);
        pager.setDatas(actorList);
        List<ActorVo> actorVoList = iMovieActorService.actorList();
        Map<String,Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("actorList",actorVoList);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        return resultInfo;
    }

    @RequestMapping("/del")
    public ResultInfo del(String ids, PageObject pager){

        System.out.println("ids="+ids);
        String arr[] = ids.split(",");
        if(iActorService.del(arr)){
            iSysLogService.add(BaseConstants.LOG_DEL,"删除了编号为："+ids+"的演员");
            int cnt = iActorService.count();
            pager.setTotalRows(cnt);
            List<ActorVo> actorList = iActorService.list(pager);
            pager.setDatas(actorList);
            resultInfo.setState(200);
            resultInfo.setData(pager);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");
            return resultInfo;
        }
        resultInfo.setFlag(false);
        resultInfo.setState(201);
        resultInfo.setErrorMsg("删除失败");
        return resultInfo;
    }

    @RequestMapping("/update")
    public ResultInfo update(int actorId){
        ActorVo actor = iActorService.getById(actorId);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setData(actor);
        return resultInfo;
    }


    @PostMapping("/save")
    public ResultInfo save(MultipartFile file1, ActorVo actor,HttpSession session,PageObject pager) throws IOException {
        if (actor.getActorId() > 0) {
            if (file1 != null) {
                if (file1.getOriginalFilename().equals("")) {
                    ActorVo a = iActorService.getById(actor.getActorId());
                    actor.setCimg(a.getCimg());
                } else {
                    String picName = UUID.randomUUID().toString();
                    // 截取文件的扩展名(如.jpg)
                    String oriName = file1.getOriginalFilename();
                    System.out.println("--上传文件名-->>" + oriName);
                    String extName = oriName.substring(oriName.lastIndexOf("."));

                    String newFileName = picName + extName;
                    File targetFile = new File(absPath, newFileName);
                    // 保存文件
                    file1.transferTo(targetFile);
                    actor.setCimg("http://localhost:8080/img/" + newFileName);
                }
            }
            if (iActorService.update(actor)) {
                iSysLogService.add(BaseConstants.LOG_UPDATE, actor.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("修改成功");
            } else {
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
                return resultInfo;
            }
        } else {
            if (file1 != null) {
                if (file1.getOriginalFilename().equals("")) {
                    actor.setCimg("");
                } else {
                    String picName = "A" + UUID.randomUUID().toString();
                    // 截取文件的扩展名(如.jpg)
                    String oriName = file1.getOriginalFilename();
                    System.out.println("--上传文件名-->>" + oriName);
                    String extName = oriName.substring(oriName.lastIndexOf("."));

                    String newFileName = picName + extName;
                    File targetFile = new File(absPath, newFileName);
                    // 保存文件
                    file1.transferTo(targetFile);
                    actor.setCimg("http://localhost:8080/img/" + newFileName);
                }
            }
            if (iActorService.add(actor)) {
                iSysLogService.add(BaseConstants.LOG_ADD, actor.toString());
                resultInfo.setState(200);
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("添加成功");
            } else {
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("添加失败失败");
                return resultInfo;

            }
        }
            int cnt = iActorService.count();
            pager.setTotalRows(cnt);
            List<ActorVo> actorList = iActorService.list(pager);
            pager.setDatas(actorList);
            resultInfo.setData(pager);
            return resultInfo;
        }

}
