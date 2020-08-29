package com.ygb.Controller;



import com.ygb.Dao.IMovieDAO;
import com.ygb.entity.AgentInfo;
import com.ygb.entity.CityVo;
import com.ygb.entity.PlanVo;
import com.ygb.entity.ProvinceVo;
import com.ygb.service.IAgentInfoService;
import com.ygb.service.IMoiveService;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    IAgentInfoService agentInfoService;
    @Resource
    ResultInfo resultInfo;

    private String imgPath = "D:/img";

    @Resource
    private IMoiveService iMoiveService;
    /**
     * 根据是否有影院名称查询影院信息
     */
    @RequestMapping("/list")
    @Transactional
    public ResultInfo list(AgentInfo agentInfo,PageObject pager){
            //分页查询时，保存查询条件
        Map params = getParams(agentInfo, pager);

        resultInfo.setData(params);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("查询成功");

        return resultInfo;

    }

    //获取查询到的参数
    private Map getParams(AgentInfo agentInfo,PageObject pageObject){
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        //获取总页数
        int cnt = agentInfoService.count(agentInfo);
        //设置总页数
        pageObject.setTotalRows(cnt);
        pageObject.setCur_page(cnt);
        //根据传入的条件查询所有的影院
        System.out.println("=======================" + pageObject.getStartRow());
        List<AgentInfo> agentInfoList = agentInfoService.list(agentInfo,pageObject);
        pageObject.setDatas(agentInfoList);
        //查询所有的城市
        List<ProvinceVo> provinceVoList = agentInfoService.province();
        //将所有数据保存到map里
        Map<Object,Object> map = new HashMap<>();
        map.put("pager",pageObject);
        map.put("agent",agentInfo);
        map.put("provinceList",provinceVoList);
        return map;
    }


    //根据省份信息获取城市信息
    @Transactional
    @RequestMapping("/cityList")
    public ResultInfo cityList(@RequestParam(value = "provinceId",required = false,defaultValue = "0") int provinceId){

        List<CityVo> cityVoList = agentInfoService.city(provinceId);

        resultInfo.setData(cityVoList);

        return resultInfo;
    }

    //新增页面显示的默认属性
    @RequestMapping("/add")
    @Transactional
    public ResultInfo add() {
        //获取所有省份
        List<ProvinceVo> provinceVoList = agentInfoService.province();
        //获取默认的一个省份的城市
        List<CityVo> cityVoList = agentInfoService.city(provinceVoList.get(0).getProvinceId());
        AgentInfo agentInfo = new AgentInfo();
        //初始化页面为启用状态
        agentInfo.setInvalid(1);
        Map<String, Object> map = new HashMap<>();
        map.put("provinceList", provinceVoList);
        map.put("cityList", cityVoList);
        map.put("agent", agentInfo);
        resultInfo.setData(map);
        return resultInfo;
    }

    //修改页面的回显
    @RequestMapping("/update")
    @Transactional
    public ResultInfo update(AgentInfo agentInfo){
        if(agentInfo.getAgentId()>0){
            //根据id获取所有信息
            agentInfo = agentInfoService.getById(agentInfo.getAgentId());
            //查询说有城市
            List<ProvinceVo> provinceVoList = agentInfoService.province();
            List<CityVo> cityVoList = null;
            if(agentInfo.getProvinceId()>0){
                cityVoList = agentInfoService.city(agentInfo.getProvinceId());
            }else{
                cityVoList = agentInfoService.city(provinceVoList.get(0).getProvinceId());
            }
            Map<String,Object> map = new HashMap<>();
            map.put("agent",agentInfo);
            map.put("provinceList",provinceVoList);
            map.put("cityList",cityVoList);

            resultInfo.setFlag(true);
            resultInfo.setData(map);
            return resultInfo;
        }else{
            return null;
        }
    }

    //修改或新增影院信息
    @RequestMapping("/save")
    @Transactional
    public ResultInfo save(AgentInfo agentInfo,MultipartFile file) throws IOException {
        if(agentInfo.getAgentId() != null){ //修改
            //获取要修改影院的id
            AgentInfo g = agentInfoService.getById(agentInfo.getAgentId());
            //判断是否上传图片
            if(file != null){
                if(file.getOriginalFilename().equals("")) {
                    //把图片地址设置为空
                    agentInfo.setAgentIcon(g.getAgentIcon());
            }else{
                    String picName = UUID.randomUUID().toString();
                    //截取空间扩展名
                    String oriName = file.getOriginalFilename();
                    String extName = oriName.substring(oriName.lastIndexOf("."));
                    String neWFileName =  picName + extName;
                    File targetFile = new File(imgPath, neWFileName);
                    //保存文件
                    file.transferTo(targetFile);
                    agentInfo.setAgentIcon("http://localhost:8080/img/" + neWFileName);
                }
            }
            int update = agentInfoService.update(agentInfo);
            if(update>0){
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
            }else{
                resultInfo.setErrorMsg("修改失败");
                resultInfo.setFlag(false);
            }
        }else { //新增
            if(file != null) {
                if (file.getOriginalFilename().equals("")) {
                    agentInfo.setAgentIcon("");
                } else {
                        String picName = UUID.randomUUID().toString();
                        //截取文件扩展名
                        String oriName = file.getOriginalFilename();
                        String extName = oriName.substring(oriName.lastIndexOf("."));

                        String newFileName = picName + extName;
                        System.out.println(newFileName);
                        System.out.println(imgPath);
                        File targetFile = new File(imgPath, newFileName);
                        //保存文件
                        file.transferTo(targetFile);
                        agentInfo.setAgentIcon("http://localhost:8080/img/" + newFileName);
                }
            }
            int add = agentInfoService.add(agentInfo);
            if(add > 0){
                resultInfo.setErrorMsg("添加成功");
                resultInfo.setFlag(true);

            }else{
            resultInfo.setErrorMsg("添加失败");
            resultInfo.setFlag(false);

            }

        }

        Map params = getParams(new AgentInfo(),new PageObject());
        resultInfo.setData(params);
        return resultInfo;

    }

    @RequestMapping("/del")
    @Transactional
    public ResultInfo del(String ids,PageObject pageObject,AgentInfo agentInfo){
        String[] arr = ids.split(",");
        if(agentInfoService.del(arr)>0){
            Map params = getParams(agentInfo,pageObject);
            resultInfo.setData(params);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");

        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("删除失败");

        }
        return resultInfo;
    }


}
