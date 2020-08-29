package com.ygb.Dao;


import com.ygb.entity.ActorVo;
import com.ygb.entity.MovieActorVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieActorDAO {

    List<MovieActorVo> list(@Param("movieId") int movieId, @Param("pager") PageObject pager);

    int count(@Param("movieId") int movieId);

    List<ActorVo> actorList();

    int add(MovieActorVo movieActor);

    int update(MovieActorVo movieActor);

    MovieActorVo getById(int movieActorId);

    int del(String[] ids);


}
