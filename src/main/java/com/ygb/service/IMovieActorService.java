package com.ygb.service;

import com.ygb.entity.ActorVo;
import com.ygb.entity.MovieActorVo;
import com.ygb.util.PageObject;

import java.util.List;

public interface IMovieActorService {

    List<MovieActorVo> list(int movieId, PageObject pager);

    int count(int movieId);

    List<ActorVo> actorList();

    boolean add(MovieActorVo movieActor);

    boolean update(MovieActorVo movieActor);

    MovieActorVo getById(int movieActorId);

    boolean del(String[] ids);
}
