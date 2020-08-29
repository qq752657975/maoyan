package com.ygb.service.Impl;

import com.ygb.Dao.MovieActorDAO;
import com.ygb.entity.ActorVo;
import com.ygb.entity.MovieActorVo;
import com.ygb.service.IMovieActorService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieActorServiceImpl implements IMovieActorService {

    @Resource
    MovieActorDAO movieActorDAO;

    @Override
    public List<MovieActorVo> list(int movieId, PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return movieActorDAO.list(movieId, pager);
    }

    @Override
    public int count(int movieId) {
        return movieActorDAO.count(movieId);
    }

    @Override
    public List<ActorVo> actorList() {
        return movieActorDAO.actorList();
    }

    @Override
    public boolean add(MovieActorVo movieActor) {
        if(movieActor != null){
            int add = movieActorDAO.add(movieActor);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(MovieActorVo movieActor) {
        if(movieActor != null){
            int add = movieActorDAO.update(movieActor);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public MovieActorVo getById(int movieActorId) {
        return movieActorDAO.getById(movieActorId);
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int del = movieActorDAO.del(ids);
            if(del > 0){
                return true;
            }
        }
        return false;
    }
}
