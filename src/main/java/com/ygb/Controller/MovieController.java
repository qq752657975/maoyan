package com.ygb.Controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.ygb.entity.*;
import com.ygb.service.IAgentInfoService;
import com.ygb.service.IMoiveService;
import com.ygb.service.ISysLogService;
import com.ygb.util.BaseConstants;
import com.ygb.util.PageObject;
import com.ygb.util.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.lang.Object;

import static com.ygb.util.BaseConstants.absPath;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Resource
    ISysLogService logService;

    @Resource
    ResultInfo resultInfo;

    @Resource
    private IAgentInfoService iAgentInfoService;

    @Resource
    IMoiveService movieService;
    //显示电影列表
    @RequestMapping("/list")
    public ResultInfo list(MovieVo movie , PageObject pager, HttpSession session){
            //将查询条件存储，方便回显使用
            session.setAttribute("movie",movie);


        //将条件和页数传入getParams方法中实现查找
        Map params = getParams(movie, pager);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setData(params);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }
    //根据条件查询电影列表
    private Map getParams(MovieVo movie , PageObject pager){
        //根据条件查询电影的总数
        int cnt = movieService.count(movie);
        pager.setTotalRows(cnt);
        //根据条件查询电影列表
        List<MovieVo> movieList = movieService.list(movie,pager);
        pager.setDatas(movieList);
        //查询所有地区
        List<AreaVo> areaList = movieService.areaList();
        //查询所有的年份
        List<MovieYearVo> yearList =movieService.yearList();
        //查询所有的电影类型
        List<MovieTypeVo> movieTypeList =movieService.movieTypeList();
        Map<String, java.lang.Object> map = new HashMap<>();
        map.put("pager",pager);
        map.put("movie",movie);
        map.put("areaList",areaList);
        map.put("yearList",yearList);
        map.put("movieTypeList",movieTypeList);
        return map;
    }
    //新增电影页面的回显
    @RequestMapping("/add")
    public ResultInfo add(){
        //查询所有地区
        List<AreaVo> areaList = movieService.areaList();
        //查询所有年份
        List<MovieYearVo> yearList =movieService.yearList();
        //查询说有的电影类别
        List<MovieTypeVo> movieTypeList =movieService.movieTypeList();
        MovieVo movie = new MovieVo();
        //排序默认设置为0
        movie.setMovieId(0);
        //将所有的信息保存map集合发往前台显示
        Map<String,Object> map = new HashMap<>();
        map.put("areaList",areaList);
        map.put("yearList",yearList);
        map.put("movieTypeList",movieTypeList);
        map.put("movie",movie);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setData(map);
        return resultInfo;
    }

    //修改页面回显
    @RequestMapping("/update")
    public ResultInfo update(MovieVo movie){
        //根据id 查询这句电影的所有信息
            movie = movieService.getById(movie.getMovieId());
        //查询所有地区
        List<AreaVo> areaList = movieService.areaList();
        //查询所有年份
        List<MovieYearVo> yearList =movieService.yearList();
        //查询所有电影类型
        List<MovieTypeVo> movieTypeList =movieService.movieTypeList();
        //用一个map集合保存，发往前台回显
        Map<String,Object> map = new HashMap<>();
        map.put("movie",movie);
        map.put("areaList",areaList);
        map.put("yearList",yearList);
        map.put("movieTypeList",movieTypeList);
        resultInfo.setData(map);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }

    //删除电影
    @RequestMapping("/del")
    public ResultInfo del(String ids,HttpSession session,PageObject pageObject){
        String [] arr = ids.split(",");
        //删除电影
        MovieVo movie = (MovieVo) session.getAttribute("movie");
        if(movieService.del(arr)){
            resultInfo.setState(200);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("删除成功");
            //在查询删除后的电影信息，更新前台数据
            Map params = getParams(movie,pageObject);
            resultInfo.setData(params);
            return resultInfo;
        }
        resultInfo.setFlag(false);
        resultInfo.setErrorMsg("删除失败");
        resultInfo.setState(201);
        return resultInfo;
    }

    //增加或修改操作
    @PostMapping("/save")
    public ResultInfo save(PageObject pageObject,MultipartFile file1, MovieVo movie,HttpSession session) throws Exception{
        MovieVo movie1 = (MovieVo) session.getAttribute("movie");
        if(movie.getMovieId() > 0) { //如果传入的值id 则表示修改操作
            //根据id 查询该影片的所有信息
            MovieVo g = movieService.getById(movie.getMovieId());
            //判断是否传入图片
            if (file1 != null){
                if (file1.getOriginalFilename().equals("")) {
                    //如果没有图片 则查询出的图片信息
                    movie.setCimg(g.getCimg());
                } else {//如果有图片信息
                    //随机生成图片,名字确保唯一性
                    String picName = UUID.randomUUID().toString();
                    // 截取文件的扩展名(如.jpg)
                    String oriName = file1.getOriginalFilename();
                    System.out.println("--上传文件名-->>" + oriName);
                    String extName = oriName.substring(oriName.lastIndexOf("."));
                    //拼接生成的图片名后扩展名
                    String newFileName = picName + extName;
                    File targetFile = new File(absPath, newFileName);
                    // 保存文件
                    file1.transferTo(targetFile);
                    movie.setCimg("http://localhost:8080/img/" + newFileName);
                }
        }
            //写入数据库
            if (movieService.update(movie)){
                //写入系统日志
                logService.add(BaseConstants.LOG_UPDATE,movie.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("修改成功");
                resultInfo.setFlag(true);
                //取出session中保存的条件查找

                Map params = getParams(movie1,pageObject);
                resultInfo.setData(params);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("修改失败");
            }
        }else { //新增操作
            if(file1 != null) {
//            System.out.println("cardPicFile="+student.getCardPicFile()+",size="+student.getCardPicFile().length());
                if (file1.getOriginalFilename().equals("")) { //判断是否传入图片
                    //没有图片，则直接赋空
                    movie.setCimg("");
                } else {
                    //随机生成图片名
                    String picName = UUID.randomUUID().toString();
                    // 截取文件的扩展名(如.jpg)
                    String oriName = file1.getOriginalFilename();
                    System.out.println("--上传文件名-->>" + oriName);
                    String extName = oriName.substring(oriName.lastIndexOf("."));
                    //拼接随机生成的图片名和扩展名
                    String newFileName = picName + extName;
                    File targetFile = new File(absPath, newFileName);
                    // 保存文件
                    file1.transferTo(targetFile);
                    movie.setCimg("http://localhost:8080/img/" + newFileName);
                }
            }
            //写入数据库
            if(movieService.add(movie)){
                logService.add(BaseConstants.LOG_ADD,movie.toString());
                resultInfo.setState(200);
                resultInfo.setErrorMsg("添加成功");
                resultInfo.setFlag(true);
                //取出session中保存的条件查找

                Map params = getParams(movie1,pageObject);
                resultInfo.setData(params);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setState(201);
                resultInfo.setErrorMsg("添加失败");
            }
        }
        return resultInfo;
    }
    //显示首页展示电影图片
    @RequestMapping("movieDesc")
    public ResultInfo movieDesc(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<MovieVo> movieVos = movieService.movieLoveList1();
        List<MovieVo> movieVos1 = movieService.movieLoveList2();

        Map<String,Object> map = new HashMap<>();
        map.put("movieVos",movieVos);
        map.put("movieVos1",movieVos1);
        map.put("sessionId",session.getId());
        resultInfo.setData(map);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }

    //前台显示搜索页面列表
    @RequestMapping("/qList")
    public ResultInfo qList(MovieVo movieVo,PageObject pageObject){
        List<MovieVo> movieList = movieService.list(movieVo,pageObject);
        for(int i =0;i<movieList.size();i++) {
            List<ActorVo> actorVoList = movieService.majorActor(movieList.get(i).getMovieId());
            movieList.get(i).setActorName(actorVoList);
        }
        resultInfo.setData(movieList);
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        return resultInfo;
    }
    //前台展示展示列表
    @RequestMapping("/lists")
    public ResultInfo lists(PageObject pageObject){
        //显示前台正在热映
        MovieVo m = new MovieVo();
        m.setPlayType(1);
        List<MovieVo> list = movieService.list(m, pageObject);
        for(int i =0;i<list.size();i++) {
            List<ActorVo> actorVoList = movieService.majorActor(list.get(i).getMovieId());
            list.get(i).setActorName(actorVoList);
        }
        m.setPlayType(2);
        List<MovieVo> list1 = movieService.list(m, pageObject);
        for(int i =0;i<list.size();i++) {
            List<ActorVo> actorVoList = movieService.majorActor(list.get(i).getMovieId());
            list.get(i).setActorName(actorVoList);
        }
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        Map<String,Object> map = new HashMap<>();
        map.put("hotMovieList",list);
        map.put("notShowMovieList",list1);
        resultInfo.setData(map);
        resultInfo.setErrorMsg("查询成功");
        return resultInfo;
    }

    //前端单个电影详情展示
    @RequestMapping("/movieInfo")
    public ResultInfo movieInfo(int movieId,PageObject pageObject){
        MovieVo byId = movieService.movieInfo(movieId);
        List<ActorVo> actorVoList = movieService.majorActor(movieId);
        List<ForumVo> forumVos = movieService.byForum(movieId,pageObject);
        byId.setActorName(actorVoList);
        byId.setForumVos(forumVos);
        resultInfo.setState(200);
        resultInfo.setFlag(true);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setData(byId);
        return resultInfo;
    }

    //前端显示当前对应的影院
    @RequestMapping("/movieAgent")
    public ResultInfo movieAgent(PlanVo planVo,PageObject pageObject){
        Set<AgentInfo> movieVos = movieService.movieAgent(planVo, pageObject);
        resultInfo.setErrorMsg("查询成功");
        resultInfo.setFlag(true);
        resultInfo.setState(200);
        resultInfo.setData(movieVos);
        return resultInfo;
    }

    //显示影院中信息、影院中的电影和排片
    @RequestMapping("/agentPlay")
    public ResultInfo agentPlay(PlanVo planVo, PageObject pageObject){
        //查询影院详情
        AgentInfo byId = iAgentInfoService.getById(planVo.getAgentId());
        //查询该影院的正在热映的电影

        List<MovieVo> movieVos = movieService.agentMovieList(planVo.getAgentId());
        for(int i = 0;i<movieVos.size();i++){
            List<ActorVo> actorVoList = movieService.majorActor(movieVos.get(i).getMovieId());
            movieVos.get(i).setActorName(actorVoList);
            planVo.setMovieId(movieVos.get(i).getMovieId());
            System.out.println(planVo.getMovieId());
            List<PlanVo> planVos = movieService.moviePlanList(planVo, pageObject);
            movieVos.get(i).setPlanVos(planVos);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("agent",byId);
        map.put("movieList",movieVos);
        resultInfo.setData(map);
        resultInfo.setState(200);
        resultInfo.setFlag(true);


        return resultInfo;
    }

}
