package com.ygb.service.Impl;

import com.ygb.Dao.IMovieDAO;
import com.ygb.entity.*;
import com.ygb.service.IMoiveService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements IMoiveService {

    @Resource
    IMovieDAO iMovieDAO;

    @Override
    public List<MovieVo> list(MovieVo movie, PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }

        return iMovieDAO.list(movie, pager);
    }

    @Override
    public int count(MovieVo movie) {

        return iMovieDAO.count(movie);
    }

    @Override
    public List<AreaVo> areaList() {
        return iMovieDAO.areaList();
    }

    @Override
    public List<MovieYearVo> yearList() {
        return iMovieDAO.yearList();
    }

    @Override
    public List<MovieTypeVo> movieTypeList() {
        return iMovieDAO.movieTypeList();
    }

    @Override
    public boolean add(MovieVo movie) {
        if(movie != null){
            int add = iMovieDAO.add(movie);
            if(add > 0 ){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(MovieVo movie) {
            if(movie != null){
                int update = iMovieDAO.update(movie);
                if(update > 0){
                    return true;
                }
            }
            return false;
    }

    @Override
    public MovieVo getById(int movieId) {
        return iMovieDAO.getById(movieId);
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int del = iMovieDAO.del(ids);
            if(del > 0){
                return true;
            }
        }
        return false;
    }

    public List<MovieVo> movieList(){
        return iMovieDAO.movieList();
    }

    @Override
    public List<MovieVo> movieLoveList() {
        return iMovieDAO.movieLoveList(1);
    }

    @Override
    public List<MovieVo> movieLoveList1() {
        return iMovieDAO.movieLoveList1();
    }

    @Override
    public List<MovieVo> movieLoveList2() {
        return iMovieDAO.movieLoveList2();
    }

    @Override
    public List<ActorVo> majorActor(int movieId) {
        return iMovieDAO.majorActor(movieId);
    }

    @Override
    public MovieVo movieInfo(int movieId) {
        return iMovieDAO.movieInfo(movieId);
    }

    @Override
    public List<ForumVo> byForum(int movieId,PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return iMovieDAO.byForum(movieId,pager);
    }

    @Override
    public Set<AgentInfo> movieAgent(PlanVo planVo, PageObject pageObject) {
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        return iMovieDAO.movieAgent(planVo,pageObject);
    }

    @Override
    public List<MovieVo> agentMovieList(int agentId) {
        return iMovieDAO.agentMovieList(agentId);
    }

    @Override
    public List<PlanVo> moviePlanList(PlanVo planVo, PageObject pageObject) {
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        System.out.println(planVo.getMovieId());
        return iMovieDAO.moviePlanList(planVo,pageObject);
    }
}
